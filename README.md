# reader

Estrutura inicial de um projeto Java para um aplicativo TTS focado em leitura de palavras em ingles com sotaque selecionavel.

Neste passo, o projeto foi apenas organizado. Nenhuma regra de negocio, endpoint, interface ou integracao com biblioteca TTS foi implementada.

## Objetivo funcional

O app deve expor endpoints HTTP simples para receber palavras em ingles e, quando a palavra estiver completa/correta e existir em ingles, acionar a leitura com o sotaque selecionado e seguir para a proxima entrada.

## Estrutura escolhida

- `reader-app`: API REST, controllers, DTOs e bootstrap.
- `reader-core`: regras de negocio, services e modelos da aplicacao.
- `reader-integration`: integracoes externas com MaryTTS e API de dicionario.
- `docs`: documentos de arquitetura e decisoes iniciais.

## Por que essa estrutura

Como agora o projeto vai seguir um MVC simples sem camada de visao, a separacao em modulos foi simplificada para refletir um backend REST em camadas. O `reader-app` recebe as requisicoes HTTP, o `reader-core` concentra a regra de negocio, e o `reader-integration` fala com servicos externos.

## Biblioteca TTS definida

A biblioteca escolhida foi `MaryTTS`.

Motivos principais:

- e open source e escrita em Java
- e uma das opcoes Java TTS mais conhecidas no GitHub
- suporta ingles americano e britanico no ecossistema da plataforma
- permite manter a engine isolada no modulo `reader-integration`
- funciona bem como base offline e local

Modo inicial de integracao definido:

- `reader-integration` vai usar `MaryTTS` embutido no app
- o sotaque inicial sera somente americano (`en_US`)
- a voz inicial definida sera `cmu-slt-hsmm`
- a validacao lexical sera feita por API externa de dicionario

Observacao:

Nesta etapa, apenas a decisao arquitetural e a definicao das dependencias-base foram registradas. Nenhuma integracao de voz foi implementada.

## Proximas decisoes tecnicas

- definir a API externa de dicionario
- definir como os sotaques/vozes serao mapeados na configuracao
- definir os contratos HTTP finais de entrada

## Endpoints planejados

- `POST /api/words/validate`: valida se a palavra existe em ingles
- `POST /api/words/read`: valida e solicita a leitura da palavra
- `GET /api/voice`: retorna a configuracao atual de voz
- `GET /api/health`: endpoint simples de verificacao da aplicacao

## Como ficou a base

```text
reader
|- reader-app
|- reader-core
|- reader-integration
|- docs
`- pom.xml
```
# auto-reader-tts
# auto-reader-tts
