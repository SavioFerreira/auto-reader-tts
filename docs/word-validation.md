# Validacao de Palavra em Ingles

## Regra funcional

A palavra digitada so pode ser considerada pronta para pronuncia quando:

1. estiver completa do ponto de vista da entrada
2. existir em ingles

## Impacto arquitetural

Essa regra fica no `reader-core`.

O `core` nao deve conhecer diretamente nenhuma API externa ou arquivo concreto de dicionario. Em vez disso, ele deve depender de uma porta de consulta lexical, implementada depois pelo modulo `reader-integration`.

## Estrategia prevista

- `reader-core` decide se deve ou nao pronunciar
- uma porta de dicionario verifica existencia da palavra em ingles
- a integracao concreta podera usar arquivo local, base embarcada ou servico externo

## Decisao ainda em aberto

A fonte de verdade para saber se a palavra existe em ingles ainda nao foi escolhida.

Possibilidades futuras:

- API externa de dicionario

## Decisao atual

A validacao da existencia da palavra sera feita por API externa.

## Restricao atual

Nenhuma implementacao sera criada agora. Apenas a estrutura e a regra foram registradas.
