# Roteiro da gravação: HeapSort (máximo 5 min)

Mostre a folha do caderno correspondente enquanto fala.

## [0:00] Item a: funcionamento (folha 1)

"Vou explicar o HeapSort. Ele usa um heap de máximo, em que todo pai é maior ou igual aos filhos. Assim, o maior elemento fica sempre na raiz, na posição 1. Os filhos de i ficam nas posições 2i e 2i + 1.

O maxHeapify pega um nó e compara com os filhos. Se algum filho for maior, troca com o maior filho e continua descendo.

O construirMaxHeap chama o maxHeapify de n/2 até 1, de baixo para cima, porque as posições depois de n/2 são folhas e já são heaps.

O heapSort constrói o heap. Depois, várias vezes, troca a raiz, que é o maior, com o último elemento do heap, diminui o tamanho e conserta a raiz com o maxHeapify. Assim o vetor vai ficando ordenado do fim para o começo."

## [1:00] Item b: maxHeapify

"Cada chamada faz duas comparações e no máximo uma troca, ou seja, custo constante, e desce um nível. Então T(h) = T(h − 1) + c, que dá O(h). Como o heap é uma árvore completa, 2 elevado a h ≤ n < 2 elevado a h + 1, então a altura é h = piso de log₂ n. Logo, o maxHeapify custa O(log n)."

## [1:30] Item c: construirMaxHeap

"Na conta simples, são n/2 chamadas de log n cada, o que dá O(n log n). Mas esse limite é folgado: metade dos nós são folhas e quase todos estão perto da base. Um nó de altura h desce no máximo h níveis, e existem no máximo n/2^h nós de altura h. Então o custo é no máximo c·n vezes a soma de h/2^h. Essa soma é no máximo 2: chamando a soma de S e fazendo S − S/2, sobra 1/2 + 1/4 + 1/8 + …, que dá 1. Então o custo é no máximo 2cn. Como também são pelo menos n/2 chamadas, o construirMaxHeap é Θ(n)."

## [2:15] Item d: pior caso

"No pior caso, o algoritmo constrói o heap em O(n) e depois faz n − 1 iterações, cada uma com uma troca e um maxHeapify de log n. Isso dá O(n log n). E não dá para fazer melhor, porque todo algoritmo que ordena por comparação precisa de pelo menos log₂(n!) comparações, e isso é pelo menos (n/2)·log₂(n/2). Então o pior caso é Θ(n log n)."

## [2:45] Item e: melhor caso

"O caso mais rápido é com todos os elementos iguais. O teste é com 'maior estrito', então o maxHeapify nunca troca e para na hora. Tudo fica linear: Θ(n). Já um vetor ordenado não ajuda: a cada iteração vai para a raiz um elemento pequeno, que precisa descer quase toda a altura. Com elementos distintos, o melhor caso continua Θ(n log n)."

## [3:15] Item f: caso médio

"O caso médio fica entre o melhor caso com elementos distintos e o pior caso, e os dois são Θ(n log n). Então o médio também é Θ(n log n). Diferente do QuickSort, o HeapSort não tem caso n², porque a altura do heap é sempre log n."

## [3:35] Item g: exemplo (folha 2 + programa rodando)

"No vetor [4, 10, 3, 5, 1, 8, 7], o construirMaxHeap começa em i = 3, e o 3 troca com o 8. Em i = 2 não muda nada. Em i = 1, o 4 troca com o 10 e depois com o 5. O heap fica [10, 5, 8, 4, 1, 3, 7].

Na ordenação, o 10 vai para o fim, o 7 sobe para a raiz e desce trocando com o 8. Depois vão para o fim o 8, o 7, o 5, o 4 e o 3, um por iteração. O resultado é [1, 3, 4, 5, 7, 8, 10], que é o mesmo que o programa mostra."

## [4:25] Fechamento

"Resumindo: o HeapSort é Θ(n log n) no pior caso e no caso médio, e Θ(n) só quando todos os elementos são iguais."
