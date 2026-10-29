/**
 * TAD que acumula as medidas coletadas durante uma ordenação.
 *
 * As comparações contadas são apenas as feitas DENTRO do procedimento de
 * particionamento (A[j] <= pivô), que é exatamente o custo "cn" que aparece
 * nas recorrências da análise de complexidade.
 */
public class Estatisticas {

    /** Comparações entre elementos feitas pelo particionamento. */
    long comparacoes;

    /** Trocas (swaps) feitas pelo particionamento. */
    long trocas;

    /** Número de vezes que o particionamento foi executado. */
    long particionamentos;

    /** Comparações gastas apenas para localizar o pivô (versão proporcional). */
    long comparacoesEscolhaPivo;

    /**
     * Altura da árvore de recursão lógica (o ramo mais longo, h_max).
     * Cada particionamento de um subarranjo no nível i gera filhos no nível i+1,
     * independentemente de o filho ser tratado por recursão ou por iteração.
     */
    int alturaMaxima;

    /**
     * Menor nível em que um ramo chega a um subarranjo trivial (tamanho <= 1),
     * ou seja, o ramo mais curto da árvore (h_min).
     */
    int alturaMinima = Integer.MAX_VALUE;

    /** Profundidade máxima REAL da pilha de chamadas recursivas. */
    int pilhaMaxima;

    void registrarNivel(int nivel) {
        if (nivel > alturaMaxima) alturaMaxima = nivel;
    }

    void registrarFolha(int nivel) {
        if (nivel < alturaMinima) alturaMinima = nivel;
    }

    void registrarPilha(int profundidade) {
        if (profundidade > pilhaMaxima) pilhaMaxima = profundidade;
    }

    public long getComparacoes() { return comparacoes; }
    public long getTrocas() { return trocas; }
    public long getParticionamentos() { return particionamentos; }
    public long getComparacoesEscolhaPivo() { return comparacoesEscolhaPivo; }
    public int getAlturaMaxima() { return alturaMaxima; }
    public int getAlturaMinima() { return alturaMinima == Integer.MAX_VALUE ? 0 : alturaMinima; }
    public int getPilhaMaxima() { return pilhaMaxima; }
}
