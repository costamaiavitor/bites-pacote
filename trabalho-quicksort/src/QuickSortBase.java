/**
 * Esqueleto comum do QuickSort. As versões diferem APENAS na escolha do pivô
 * (método escolherPivo); o particionamento é sempre o de Lomuto.
 *
 * Tratamento de StackOverflowError:
 *  Em vez de fazer duas chamadas recursivas, o algoritmo faz a chamada
 *  recursiva somente para a MENOR das duas partes e trata a MAIOR por
 *  iteração (laço while), reaproveitando o mesmo quadro de pilha
 *  ("eliminação da recursão de cauda"). Como a menor parte tem no máximo
 *  metade dos elementos, cada nova chamada recursiva recebe no máximo n/2
 *  elementos e a profundidade da pilha fica limitada a log2(n), mesmo no
 *  pior caso (partições 0 e n-1). O tempo continua o mesmo; só o uso de
 *  memória da pilha muda de O(n) para O(log n).
 */
public abstract class QuickSortBase implements Ordenador {

    protected Estatisticas est = new Estatisticas();

    /**
     * Devolve o ÍNDICE (entre p e r) do elemento que será usado como pivô
     * para particionar A[p..r].
     */
    protected abstract int escolherPivo(int[] A, int p, int r);

    @Override
    public void ordenar(int[] A) {
        est = new Estatisticas();
        if (A.length > 0) {
            quickSort(A, 0, A.length - 1, 0, 1);
        }
    }

    /**
     * @param nivel nível do subarranjo A[p..r] na árvore de recursão (raiz = 0)
     * @param pilha profundidade real desta chamada na pilha de execução
     */
    private void quickSort(int[] A, int p, int r, int nivel, int pilha) {
        est.registrarPilha(pilha);
        while (p < r) {
            est.registrarNivel(nivel);

            // 1) escolhe o pivô e o coloca no fim, onde o Lomuto o espera
            int iPivo = escolherPivo(A, p, r);
            Particionamento.trocar(A, iPivo, r);

            // 2) particiona: A[p..q-1] <= A[q] < A[q+1..r]
            int q = Particionamento.lomuto(A, p, r, est);

            // 3) recursão na menor parte, iteração na maior
            if (q - p < r - q) {
                quickSort(A, p, q - 1, nivel + 1, pilha + 1);
                p = q + 1;
            } else {
                quickSort(A, q + 1, r, nivel + 1, pilha + 1);
                r = q - 1;
            }
            nivel++;   // a parte maior também é filha (nível + 1) na árvore
        }
        est.registrarFolha(nivel); // subarranjo de tamanho 0 ou 1: folha
    }

    @Override
    public Estatisticas estatisticas() {
        return est;
    }
}
