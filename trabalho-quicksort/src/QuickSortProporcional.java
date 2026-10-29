/**
 * QuickSort Proporcional: recebe alfa (0 < alfa <= 1/2) e escolhe o pivô de
 * forma que o subarranjo de n elementos seja SEMPRE dividido em partes de
 * tamanho  k = floor(alfa*(n-1))  e  n-1-k, ou seja, na razão alfa:(1-alfa).
 *
 * Como funciona a escolha:
 *  O pivô precisa ser o elemento que ocupará a posição p+k após a ordenação
 *  (o (k+1)-ésimo menor de A[p..r]). As entradas são permutações de 1..n e o
 *  QuickSort tem a seguinte invariante: o subarranjo A[p..r] (índices a partir
 *  de 0) contém exatamente os valores que pertencem a essas posições no vetor
 *  ordenado, isto é, {p+1, ..., r+1}. Logo, o pivô desejado é o VALOR p+1+k,
 *  e basta uma busca linear em A[p..r] para achar sua posição.
 *
 *  Essa busca custa Theta(n), o mesmo que o particionamento, então só muda a
 *  constante c do termo cn da recorrência, não a complexidade assintótica.
 *  (Para entradas arbitrárias, o mesmo efeito seria obtido com um algoritmo de
 *  seleção em tempo linear, como a "mediana das medianas", também Theta(n).)
 *
 * Com alfa = 0 obtém-se o caso NÃO proporcional (partições 0 e n-1), usado
 * apenas para demonstrar o comportamento quadrático.
 */
public class QuickSortProporcional extends QuickSortBase {

    private final double alfa;
    private final String rotulo;

    public QuickSortProporcional(double alfa, String rotulo) {
        if (alfa < 0 || alfa > 0.5) {
            throw new IllegalArgumentException("alfa deve estar em [0, 1/2]: " + alfa);
        }
        this.alfa = alfa;
        this.rotulo = rotulo;
    }

    @Override
    protected int escolherPivo(int[] A, int p, int r) {
        int n = r - p + 1;
        int k = (int) Math.floor(alfa * (n - 1) + 1e-9); // tamanho da parte esquerda
        int valorAlvo = p + 1 + k;
        for (int i = p; i <= r; i++) {
            est.comparacoesEscolhaPivo++;
            if (A[i] == valorAlvo) {
                return i;
            }
        }
        throw new IllegalStateException(
                "A entrada nao e uma permutacao de 1..n (valor " + valorAlvo + " ausente)");
    }

    public double getAlfa() {
        return alfa;
    }

    @Override
    public String nome() {
        return "QuickSort proporcional alfa=" + rotulo;
    }
}
