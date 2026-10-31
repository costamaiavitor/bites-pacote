# Roteiro da gravação: HeapSort (até 3 min)

Tudo escrito do jeito que se fala, inclusive as contas.

## Como ler os símbolos

| Escrito | Fala |
|---|---|
| log₂ n | "log de n na base 2" (ou só "log de n") |
| ⌊log₂ n⌋ | "piso de log de n na base 2" |
| O(log n) | "O de log de n" |
| Θ(n) | "theta de n" |
| Θ(n log n) | "theta de n log n" |
| 2ʰ | "2 elevado a h" |
| n/2 | "n sobre 2" (ou "metade de n") |
| h/2ʰ | "h sobre 2 elevado a h" |
| T(h) = T(h − 1) + c | "T de h é igual a T de h menos 1, mais c" |
| log₂(n!) | "log na base 2 de n fatorial" |
| n² | "n ao quadrado" |

## [0:00] a) Funcionamento

"O HeapSort usa um heap de máximo, em que todo pai é maior ou igual aos filhos, então o maior fica na raiz. O maxHeapify faz um elemento descer, trocando com o maior filho. O construirMaxHeap chama o maxHeapify de n sobre 2 até 1 e monta o heap. O heapSort troca a raiz com o último elemento, diminui o heap e conserta a raiz, até ordenar tudo."

## [0:30] b) maxHeapify

"Cada chamada tem custo constante e desce um nível. Então T de h é igual a T de h menos 1, mais c, o que dá O de h. Como o heap é uma árvore completa, a altura h é o piso de log de n na base 2. Então o maxHeapify é O de log de n."

## [0:50] c) construirMaxHeap

"Pela conta simples seria n sobre 2 vezes log de n, ou seja, O de n log n. Mas a maioria dos nós está embaixo e desce pouco. O custo é no máximo c vezes n vezes a soma de h sobre 2 elevado a h, e essa soma é no máximo 2. Então o custo é no máximo 2 c n, e o construirMaxHeap é theta de n."

## [1:15] d) Pior caso

"São n menos 1 iterações, cada uma com um maxHeapify de log de n, o que dá O de n log n. E todo algoritmo por comparação precisa de pelo menos log na base 2 de n fatorial comparações, que é da ordem de n log n. Então o pior caso é theta de n log n."

## [1:40] e) Melhor caso

"Com todos os elementos iguais, o maxHeapify nunca troca, e o algoritmo é theta de n. Um vetor ordenado não ajuda, porque o elemento que vai para a raiz é pequeno e desce quase toda a altura."

## [1:55] f) Caso médio

"O caso médio fica entre o melhor e o pior, que com elementos distintos são os dois theta de n log n. Então o médio também é theta de n log n, e não existe caso n ao quadrado como no QuickSort."

## [2:10] g) Exemplo (mostrar o programa)

"No vetor 4, 10, 3, 5, 1, 8, 7, o heap montado fica 10, 5, 8, 4, 1, 3, 7. Depois, a cada iteração, o maior vai para o fim: 10, 8, 7, 5, 4 e 3. O resultado é 1, 3, 4, 5, 7, 8, 10, igual ao que o programa mostra."

## [2:35] Fechamento

"Então o HeapSort é theta de n log n no pior caso e no caso médio, e theta de n só quando todos os elementos são iguais."
