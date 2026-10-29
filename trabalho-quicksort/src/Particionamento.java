/**
 * Procedimento de particionamento ÚNICO usado por todas as versões do QuickSort.
 *
 * Esquema escolhido: LOMUTO (Cormen et al., "Algoritmos", cap. 7).
 *
 * Justificativa da escolha:
 *  1. Ao final, o pivô fica na sua posição DEFINITIVA q, e o subarranjo é
 *     dividido em exatamente A[p..q-1] (elementos <= pivô) e A[q+1..r]
 *     (elementos > pivô). Os tamanhos dos subproblemas são, portanto,
 *     exatamente k e n-1-k, o que casa diretamente com as recorrências
 *     T(n) = T(k) + T(n-1-k) + cn usadas na análise.
 *  2. Isso permite FORÇAR uma divisão alfa:(1-alfa) apenas escolhendo qual
 *     elemento será o pivô (basta escolher o elemento de posto k).
 *     No esquema de Hoare o pivô não termina necessariamente na sua posição
 *     final, e os tamanhos das partes dependem da distribuição dos dados,
 *     o que dificultaria controlar a proporção.
 *  3. O custo é exatamente n-1 comparações para um subarranjo de n elementos,
 *     fácil de contar e de analisar (Theta(n)).
 *
 * Como as entradas são permutações (sem valores repetidos), a fraqueza
 * conhecida do Lomuto com muitos elementos iguais não ocorre.
 */
public final class Particionamento {

    private Particionamento() { }

    /**
     * Particiona A[p..r] usando A[r] como pivô.
     *
     * @return a posição final q do pivô, com A[p..q-1] <= A[q] < A[q+1..r]
     */
    public static int lomuto(int[] A, int p, int r, Estatisticas est) {
        int pivo = A[r];
        int i = p - 1;                       // fim da região dos "menores ou iguais"
        for (int j = p; j < r; j++) {        // n-1 iterações
            est.comparacoes++;
            if (A[j] <= pivo) {
                i++;
                trocar(A, i, j);
                est.trocas++;
            }
        }
        trocar(A, i + 1, r);                 // pivô vai para a posição definitiva
        est.trocas++;
        est.particionamentos++;
        return i + 1;
    }

    public static void trocar(int[] A, int i, int j) {
        int aux = A[i];
        A[i] = A[j];
        A[j] = aux;
    }
}
