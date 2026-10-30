import java.util.Arrays;

/*
 * HeapSort com heap de maximo.
 * O vetor usa as posicoes 1..n (a posicao 0 nao e usada) para ficar igual ao
 * pseudocodigo: filho esquerdo de i = 2i, filho direito = 2i + 1.
 */
public class HeapSort {

    // troca o conteudo das posicoes i e j
    static void troca(int[] A, int i, int j) {
        int aux = A[i];
        A[i] = A[j];
        A[j] = aux;
    }

    // faz o elemento da posicao i descer ate a subarvore de i virar um heap de maximo
    // (supondo que as subarvores dos filhos de i ja sao heaps)
    static void maxHeapify(int[] A, int i, int tam) {
        int esq = 2 * i;
        int dir = 2 * i + 1;
        int maior = i;
        if (esq <= tam && A[esq] > A[maior]) maior = esq;
        if (dir <= tam && A[dir] > A[maior]) maior = dir;
        if (maior != i) {
            troca(A, i, maior);
            maxHeapify(A, maior, tam);
        }
    }

    // transforma A[1..n] em heap de maximo, de baixo para cima
    // (os nos de n/2 + 1 ate n sao folhas e ja sao heaps)
    static void construirMaxHeap(int[] A, int n) {
        for (int i = n / 2; i >= 1; i--) {
            maxHeapify(A, i, n);
        }
    }

    // coloca o maior (raiz) no fim, diminui o heap e conserta a raiz
    static void heapSort(int[] A, int n) {
        construirMaxHeap(A, n);
        System.out.println("Heap apos construirMaxHeap: " + mostra(A, n, n));
        int tam = n;
        for (int i = n; i >= 2; i--) {
            troca(A, 1, i);
            tam = tam - 1;
            maxHeapify(A, 1, tam);
            System.out.println("i = " + i + ": " + mostra(A, n, tam));
        }
    }

    // mostra A[1..n] separando o heap (1..tam) da parte ja ordenada
    static String mostra(int[] A, int n, int tam) {
        StringBuilder s = new StringBuilder("[");
        for (int k = 1; k <= n; k++) {
            s.append(A[k]);
            if (k == tam && tam < n) s.append(" |");
            if (k < n) s.append(" ");
        }
        return s.append("]").toString();
    }

    public static void main(String[] args) {
        int[] entrada = {4, 10, 3, 5, 1, 8, 7};
        int n = entrada.length;

        int[] A = new int[n + 1]; // A[0] nao e usado
        for (int k = 0; k < n; k++) A[k + 1] = entrada[k];

        System.out.println("Vetor inicial: " + mostra(A, n, n));
        heapSort(A, n);
        System.out.println("Vetor ordenado: " + Arrays.toString(Arrays.copyOfRange(A, 1, n + 1)));
    }
}
