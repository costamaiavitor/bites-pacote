# Roteiro da gravação: HeapSort (até 3 min)

## Como ler os símbolos

| Escrito | Fala |
|---|---|
| log₂ n | "log de n na base 2" (depois, só "log de n") |
| ⌊log₂ n⌋ | "piso de log de n na base 2" |
| O(log n) | "O de log de n" |
| Θ(n log n) | "theta de n log n" |
| 2ʰ | "2 elevado a h" |
| h/2ʰ | "h sobre 2 elevado a h" |
| log₂(n!) | "log na base 2 de n fatorial" |
| n² | "n ao quadrado" |

## [0:00] a) Funcionamento

"O HeapSort usa um heap de máximo, em que todo pai é maior ou igual aos filhos, então o maior fica na raiz. O maxHeapify faz um elemento descer, trocando com o maior filho. O construirMaxHeap chama o maxHeapify de n/2 até 1 e monta o heap. O heapSort troca a raiz com o último elemento, diminui o heap e conserta a raiz, até ordenar tudo."

## [0:30] b) maxHeapify

"Cada chamada tem custo constante e desce um nível. Então T(h) = T(h − 1) + c, o que dá O(h). Como o heap é uma árvore completa, h = ⌊log₂ n⌋. Então o maxHeapify é O(log n)."

## [0:50] c) construirMaxHeap

"Pela conta simples seria n/2 vezes log n, ou seja, O(n log n). Mas a maioria dos nós está embaixo e desce pouco. O custo é no máximo c·n vezes a soma de h/2ʰ, e essa soma é no máximo 2. Então o custo é no máximo 2cn, e o construirMaxHeap é Θ(n)."

## [1:15] d) Pior caso

"São n − 1 iterações, cada uma com um maxHeapify de log n, o que dá O(n log n). E todo algoritmo por comparação precisa de pelo menos log₂(n!) comparações, que é da ordem de n log n. Então o pior caso é Θ(n log n)."

## [1:40] e) Melhor caso

"Com todos os elementos iguais, o maxHeapify nunca troca, e o algoritmo é Θ(n). Um vetor ordenado não ajuda, porque o elemento que vai para a raiz é pequeno e desce quase toda a altura."

## [1:55] f) Caso médio

"O caso médio fica entre o melhor e o pior, que com elementos distintos são os dois Θ(n log n). Então o médio também é Θ(n log n), e não existe caso n² como no QuickSort."

## [2:10] g) Exemplo (mostrar o programa)

"No vetor [4, 10, 3, 5, 1, 8, 7], o heap montado fica [10, 5, 8, 4, 1, 3, 7]. Depois, a cada iteração, o maior vai para o fim: 10, 8, 7, 5, 4 e 3. O resultado é [1, 3, 4, 5, 7, 8, 10], igual ao que o programa mostra."

## [2:35] Fechamento

"Então o HeapSort é Θ(n log n) no pior caso e no caso médio, e Θ(n) só quando todos os elementos são iguais."
