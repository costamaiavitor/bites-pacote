import java.util.Random;

/**
 * Gera os vetores de entrada: permutações aleatórias de 1..n, sem repetição.
 *
 * Algoritmo de Fisher-Yates (Knuth shuffle): começa com 1, 2, ..., n e, de
 * trás para frente, troca cada posição i com uma posição sorteada em [0, i].
 * Cada uma das n! permutações tem a mesma probabilidade e o custo é Theta(n).
 */
public final class GeradorPermutacoes {

    private final Random rnd;

    public GeradorPermutacoes(long semente) {
        this.rnd = new Random(semente);
    }

    public int[] gerar(int n) {
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = i + 1;
        }
        for (int i = n - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int aux = v[i];
            v[i] = v[j];
            v[j] = aux;
        }
        return v;
    }

    /** Verifica se o vetor está ordenado e é exatamente 1, 2, ..., n. */
    public static boolean estaOrdenado(int[] v) {
        for (int i = 0; i < v.length; i++) {
            if (v[i] != i + 1) {
                return false;
            }
        }
        return true;
    }
}
