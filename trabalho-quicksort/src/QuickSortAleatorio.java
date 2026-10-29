import java.util.Random;

/**
 * QuickSort com pivô aleatório: o pivô de A[p..r] é sorteado de forma
 * uniforme entre as r-p+1 posições. Assim, todas as partições
 * (0, n-1), (1, n-2), ..., (n-1, 0) são igualmente prováveis, qualquer que
 * seja a entrada.
 */
public class QuickSortAleatorio extends QuickSortBase {

    private final Random rnd;

    public QuickSortAleatorio(long semente) {
        this.rnd = new Random(semente);
    }

    @Override
    protected int escolherPivo(int[] A, int p, int r) {
        return p + rnd.nextInt(r - p + 1);
    }

    @Override
    public String nome() {
        return "QuickSort pivo aleatorio";
    }
}
