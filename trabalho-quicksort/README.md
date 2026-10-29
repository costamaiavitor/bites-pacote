# Trabalho: QuickSort em Java (pivô aleatório e partição proporcional)

- `src/`: código-fonte Java (comentado)
- `DOCUMENTACAO.pdf` / `DOCUMENTACAO.md`: introdução, implementação e análise de complexidade
- `resultados.csv`: saída dos experimentos

## Compilar e executar (Java 17+)

```
javac -d bin src/*.java
java -cp bin Main
```

Opções: `--n 1000,10000,100000,1000000`, `--rep 5`, `--semente 42`,
`--degenerado 1000,5000,20000`, `--csv resultados.csv`.
