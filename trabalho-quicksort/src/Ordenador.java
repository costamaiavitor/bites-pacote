/** TAD "ordenador": qualquer algoritmo que ordena um arranjo de inteiros. */
public interface Ordenador {

    /** Ordena o arranjo em ordem crescente (no próprio arranjo). */
    void ordenar(int[] A);

    /** Nome legível da versão, usado nos relatórios. */
    String nome();

    /** Medidas coletadas na última chamada de ordenar(). */
    Estatisticas estatisticas();
}
