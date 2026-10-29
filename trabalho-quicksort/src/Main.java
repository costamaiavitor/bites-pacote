import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Programa principal: gera permutações aleatórias de 1..n, ordena com cada
 * versão do QuickSort, confere o resultado e compara as medidas obtidas com
 * as previsões da análise de complexidade.
 *
 * Uso:
 *   java Main [--n 1000,10000,100000,1000000] [--rep 5] [--semente 42]
 *             [--degenerado 1000,5000,20000] [--csv resultados.csv]
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);

        int[] tamanhos = {1_000, 10_000, 100_000, 1_000_000};
        int[] tamanhosDegenerado = {1_000, 5_000, 20_000};
        int repeticoes = 5;
        long semente = 42;
        String arquivoCsv = "resultados.csv";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--n" -> tamanhos = lerLista(args[++i]);
                case "--rep" -> repeticoes = Integer.parseInt(args[++i]);
                case "--semente" -> semente = Long.parseLong(args[++i]);
                case "--degenerado" -> tamanhosDegenerado = lerLista(args[++i]);
                case "--csv" -> arquivoCsv = args[++i];
                default -> {
                    System.err.println("Argumento desconhecido: " + args[i]);
                    System.exit(1);
                }
            }
        }

        List<Ordenador> versoes = new ArrayList<>();
        versoes.add(new QuickSortAleatorio(semente));
        versoes.add(new QuickSortProporcional(1.0 / 2, "1/2"));
        versoes.add(new QuickSortProporcional(1.0 / 3, "1/3"));
        versoes.add(new QuickSortProporcional(1.0 / 4, "1/4"));
        versoes.add(new QuickSortProporcional(1.0 / 5, "1/5"));

        try (PrintWriter csv = new PrintWriter(new FileWriter(arquivoCsv))) {
            csv.println("versao,n,comparacoes_medias,previsao,razao_cmp_nlog2n,"
                    + "altura_min,altura_max,pilha_max,tempo_ms");

            System.out.println("=== Experimento 1: permutacoes aleatorias de 1..n ("
                    + repeticoes + " repeticoes por tamanho) ===\n");
            for (Ordenador alg : versoes) {
                System.out.println(alg.nome());
                System.out.printf("%10s %16s %16s %10s %8s %6s %6s %6s %6s %6s %10s%n",
                        "n", "comparacoes", "previsao", "C/nlog2n", "assint.",
                        "hmin", "prev", "hmax", "prev", "pilha", "tempo(ms)");
                for (int n : tamanhos) {
                    executar(alg, n, repeticoes, semente, csv);
                }
                System.out.println();
            }

            System.out.println("=== Experimento 2: particao NAO proporcional (sempre 0 e n-1) ===\n");
            Ordenador degenerado = new QuickSortProporcional(0.0, "0 (degenerado)");
            System.out.println(degenerado.nome());
            System.out.printf("%10s %16s %16s %10s %8s %6s %6s %6s %6s %6s %10s%n",
                    "n", "comparacoes", "n(n-1)/2", "C/nlog2n", "",
                    "hmin", "", "hmax", "", "pilha", "tempo(ms)");
            for (int n : tamanhosDegenerado) {
                executar(degenerado, n, 1, semente, csv);
            }
            System.out.println("\nObs.: a altura da arvore chega a n-2, mas a pilha real fica em 2 chamadas,");
            System.out.println("porque a parte maior e tratada por iteracao (sem StackOverflowError).");
        }
        System.out.println("\nResultados gravados em " + arquivoCsv);
    }

    private static void executar(Ordenador alg, int n, int repeticoes, long semente,
                                 PrintWriter csv) {
        GeradorPermutacoes gerador = new GeradorPermutacoes(semente + n);
        double somaComparacoes = 0;
        long somaTempo = 0;
        int hMin = Integer.MAX_VALUE, hMax = 0, pilha = 0;

        for (int rep = 0; rep < repeticoes; rep++) {
            int[] v = gerador.gerar(n);
            long inicio = System.nanoTime();
            alg.ordenar(v);
            somaTempo += System.nanoTime() - inicio;

            if (!GeradorPermutacoes.estaOrdenado(v)) {
                throw new IllegalStateException(alg.nome() + " nao ordenou corretamente (n=" + n + ")");
            }
            Estatisticas e = alg.estatisticas();
            somaComparacoes += e.getComparacoes();
            hMin = Math.min(hMin, e.getAlturaMinima());
            hMax = Math.max(hMax, e.getAlturaMaxima());
            pilha = Math.max(pilha, e.getPilhaMaxima());
        }

        double comparacoes = somaComparacoes / repeticoes;
        double tempoMs = somaTempo / 1e6 / repeticoes;
        double nlog2n = n * log2(n);
        double previsao;
        String constante = "";
        String prevHmin = "", prevHmax = "";

        if (alg instanceof QuickSortProporcional qp && qp.getAlfa() > 0) {
            double a = qp.getAlfa();
            previsao = recorrenciaProporcional(n, a, new HashMap<>()); // valor exato
            constante = String.format("%.3f", 1 / entropia(a));      // C ~ n log2 n / H(alfa)
            prevHmin = String.format("%.1f", Math.log(n) / Math.log(1 / a));
            prevHmax = String.format("%.1f", Math.log(n) / Math.log(1 / (1 - a)));
        } else if (alg instanceof QuickSortProporcional) {
            previsao = (double) n * (n - 1) / 2;                   // caso degenerado
        } else {
            previsao = 2.0 * (n + 1) * harmonico(n) - 4.0 * n;     // valor esperado exato
            constante = String.format("%.3f", 2 * Math.log(2));     // C ~ 1,386 n log2 n
        }

        System.out.printf("%10d %16.0f %16.0f %10.3f %8s %6d %6s %6d %6s %6d %10.2f%n",
                n, comparacoes, previsao, comparacoes / nlog2n, constante,
                hMin, prevHmin, hMax, prevHmax, pilha, tempoMs);
        csv.printf("%s,%d,%.0f,%.0f,%.4f,%d,%d,%d,%.3f%n",
                alg.nome(), n, comparacoes, previsao, comparacoes / nlog2n,
                hMin, hMax, pilha, tempoMs);
    }

    /**
     * Resolve numericamente a recorrência da versão proporcional:
     * C(n) = (n-1) + C(k) + C(n-1-k), com k = floor(alfa*(n-1)) e C(0) = C(1) = 0.
     */
    static double recorrenciaProporcional(int n, double alfa, Map<Integer, Double> memo) {
        if (n <= 1) return 0;
        Double conhecido = memo.get(n);
        if (conhecido != null) return conhecido;
        int k = (int) Math.floor(alfa * (n - 1) + 1e-9);
        double c = (n - 1) + recorrenciaProporcional(k, alfa, memo)
                + recorrenciaProporcional(n - 1 - k, alfa, memo);
        memo.put(n, c);
        return c;
    }

    static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    /** H(a) = -a log2 a - (1-a) log2 (1-a): entropia binária. */
    static double entropia(double a) {
        return -a * log2(a) - (1 - a) * log2(1 - a);
    }

    /** Número harmônico H_n = 1 + 1/2 + ... + 1/n. */
    static double harmonico(int n) {
        double s = 0;
        for (int i = 1; i <= n; i++) {
            s += 1.0 / i;
        }
        return s;
    }

    private static int[] lerLista(String s) {
        String[] partes = s.split(",");
        int[] v = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            v[i] = Integer.parseInt(partes[i].trim().replace("_", ""));
        }
        return v;
    }
}
