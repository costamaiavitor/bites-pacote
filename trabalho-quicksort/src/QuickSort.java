import java.util.Random;

/*
 * Trabalho AV-2 - QuickSort
 *
 * Duas versoes do QuickSort que usam o MESMO particionamento (Lomuto):
 *   1) QuickSort com pivo aleatorio
 *   2) QuickSort Proporcional, que recebe alfa e forca a divisao alfa : (1 - alfa)
 *
 * Os vetores de teste sao permutacoes aleatorias de 1 ate n (sem repeticao).
 */
public class QuickSort {

    // gerador de numeros aleatorios (usado no pivo aleatorio e para embaralhar os vetores)
    static Random rand = new Random();

    // quantas vezes cada teste e repetido para tirar a media do tempo
    static final int REPETICOES = 5;

    /*
     * Particionamento de Lomuto.
     * Usa v[fim] como pivo e reorganiza v[ini..fim] assim:
     *   v[ini..q-1] <= pivo,  v[q] = pivo,  v[q+1..fim] > pivo
     * Devolve q, a posicao final do pivo.
     *
     * i = "cercadinho": marca o fim da regiao dos elementos <= pivo
     * j = "olheiro": percorre o vetor uma unica vez, de ini ate fim-1
     */
    static int particionar(int[] v, int ini, int fim) {
        int pivo = v[fim];
        int i = ini - 1; // no inicio o cercadinho esta vazio

        for (int j = ini; j < fim; j++) {
            // se o olheiro achou alguem <= pivo, aumenta o cercadinho e coloca ele la dentro
            if (v[j] <= pivo) {
                i++;
                trocar(v, i, j);
            }
        }

        // o pivo vai para logo depois do cercadinho: essa e a posicao definitiva dele
        trocar(v, i + 1, fim);
        return i + 1;
    }

    // troca o conteudo das posicoes i e j do vetor
    static void trocar(int[] v, int i, int j) {
        int aux = v[i];
        v[i] = v[j];
        v[j] = aux;
    }

    /*
     * Versao i: QuickSort com pivo aleatorio.
     *
     * O pivo e sorteado entre ini e fim e trocado com v[fim], porque o
     * Lomuto sempre usa o ultimo elemento como pivo.
     *
     * Para evitar StackOverflowError: a recursao e feita so no pedaco MENOR
     * e o pedaco MAIOR e resolvido pelo proprio while (ini ou fim sao
     * atualizados e o laco continua). Como o pedaco menor tem no maximo
     * metade dos elementos, a pilha nunca passa de log2(n) chamadas.
     */
    static void quickSortAleatorio(int[] v, int ini, int fim) {
        while (ini < fim) {
            int p = ini + rand.nextInt(fim - ini + 1); // posicao sorteada entre ini e fim
            trocar(v, p, fim);                         // pivo vai para o fim (exigencia do Lomuto)
            int q = particionar(v, ini, fim);

            // esquerda tem q - ini elementos, direita tem fim - q elementos
            if (q - ini < fim - q) {
                quickSortAleatorio(v, ini, q - 1); // recursao no lado menor (esquerda)
                ini = q + 1;                       // o laco continua no lado maior (direita)
            } else {
                quickSortAleatorio(v, q + 1, fim); // recursao no lado menor (direita)
                fim = q - 1;                       // o laco continua no lado maior (esquerda)
            }
        }
    }

    /*
     * Versao ii: QuickSort Proporcional.
     *
     * Para um pedaco de n elementos, queremos k = (int)(alfa * (n - 1))
     * elementos a esquerda do pivo e n - 1 - k a direita (o pivo fica fora).
     * Entao o pivo tem que ser o (k+1)-esimo menor elemento do pedaco.
     *
     * Por que valor = ini + 1 + k:
     * o vetor e uma permutacao de 1 a n, e depois de cada particionamento o
     * pedaco v[ini..fim] contem exatamente os valores que vao ficar nessas
     * posicoes no vetor ordenado, ou seja, ini+1, ini+2, ..., fim+1
     * (+1 porque o vetor comeca na posicao 0 e os valores comecam em 1).
     * O menor deles e ini+1, entao o (k+1)-esimo menor e ini + 1 + k.
     * Basta procurar onde esse valor esta (busca linear, custo n).
     *
     * O tratamento do StackOverflowError e o mesmo da versao aleatoria.
     */
    static void quickSortProporcional(int[] v, int ini, int fim, double alfa) {
        while (ini < fim) {
            int n = fim - ini + 1;
            int k = (int) (alfa * (n - 1)); // tamanho do lado esquerdo (arredonda para baixo)
            int valor = ini + 1 + k;        // valor que deve ser o pivo

            int p = ini;                    // procura a posicao do pivo
            while (v[p] != valor) {
                p++;
            }

            trocar(v, p, fim);              // pivo vai para o fim (exigencia do Lomuto)
            int q = particionar(v, ini, fim);

            if (q - ini < fim - q) {
                quickSortProporcional(v, ini, q - 1, alfa);
                ini = q + 1;
            } else {
                quickSortProporcional(v, q + 1, fim, alfa);
                fim = q - 1;
            }
        }
    }

    /*
     * Gera uma permutacao aleatoria de 1 ate n, sem repeticao.
     * Preenche 1, 2, ..., n e embaralha (algoritmo de Fisher-Yates):
     * de tras para frente, troca cada posicao i com uma posicao sorteada entre 0 e i.
     */
    static int[] gerarVetor(int n) {
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = i + 1;
        }
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            trocar(v, i, j);
        }
        return v;
    }

    // confere se o vetor ficou exatamente 1, 2, ..., n
    static boolean estaOrdenado(int[] v) {
        for (int i = 0; i < v.length; i++) {
            if (v[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    /*
     * Para cada tamanho n: gera vetores novos, ordena com cada versao,
     * confere se ficou ordenado e imprime o tempo medio em milissegundos.
     */
    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 1000000};
        double[] alfas = {0.5, 0.25}; // alfa = 1/2 e alfa = 1/4

        for (int n : tamanhos) {
            System.out.println("n = " + n);

            // versao i: pivo aleatorio
            long soma = 0;
            boolean ok = true;
            for (int r = 0; r < REPETICOES; r++) {
                int[] v = gerarVetor(n);
                long inicio = System.nanoTime();
                quickSortAleatorio(v, 0, n - 1);
                soma += System.nanoTime() - inicio;
                ok = ok && estaOrdenado(v);
            }
            System.out.printf("  Aleatorio:                   %8.2f ms  ordenado = %b%n",
                    soma / 1e6 / REPETICOES, ok);

            // versao ii: proporcional, para cada alfa
            for (double alfa : alfas) {
                soma = 0;
                ok = true;
                for (int r = 0; r < REPETICOES; r++) {
                    int[] v = gerarVetor(n);
                    long inicio = System.nanoTime();
                    quickSortProporcional(v, 0, n - 1, alfa);
                    soma += System.nanoTime() - inicio;
                    ok = ok && estaOrdenado(v);
                }
                System.out.printf("  Proporcional (alfa = %.2f):  %8.2f ms  ordenado = %b%n",
                        alfa, soma / 1e6 / REPETICOES, ok);
            }
        }
    }
}
