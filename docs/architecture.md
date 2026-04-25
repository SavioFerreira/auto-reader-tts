# Arquitetura Inicial

## Direcao geral

O projeto foi preparado como um backend REST simples em MVC sem camada de visao.

Fluxo principal previsto:

`controller -> service -> integration`

O projeto foi dividido em tres responsabilidades:

1. `reader-core`
   Responsavel pelo fluxo principal da aplicacao:
   - receber a palavra
   - validar estado da entrada
   - verificar se a palavra existe em ingles
   - decidir quando pronunciar
   - solicitar reproducao com o sotaque selecionado

2. `reader-integration`
   Responsavel pelas integracoes externas:
   - inicializacao da biblioteca escolhida
   - configuracao de voz/sotaque
   - chamada da API externa de dicionario
   - adaptacao de parametros tecnicos da engine TTS

3. `reader-app`
   Responsavel pela API REST:
   - controllers
   - DTOs de request/response
   - bootstrap
   - configuracao web

## Organizacao interna planejada

### reader-app

- `controller/`: endpoints REST
- `dto/request/`: contratos de entrada HTTP
- `dto/response/`: contratos de saida HTTP
- `config/`: configuracao da aplicacao web
- `exception/`: tratamento HTTP de erros

### reader-core

- `service/`: orquestracao da regra de negocio
- `model/`: modelos centrais do dominio da aplicacao
- `validation/`: regras de validacao da palavra e do fluxo
- `domain/word/`: conceitos ligados a palavra digitada e seu estado de validade
- `ports/dictionary/`: contrato futuro para consulta lexical

### reader-integration

- `tts/`: integracao com MaryTTS
- `dictionary/`: integracao com API externa de dicionario
- `config/`: configuracao de clientes externos

## Endpoints iniciais planejados

- `POST /api/words/validate`
- `POST /api/words/read`
- `GET /api/voice`
- `GET /api/health`

## Decisoes mantidas em aberto

- API externa de dicionario a ser usada
- formato final dos payloads HTTP
- estrategia futura para suportar mais sotaques

## Decisoes ja tomadas

- arquitetura: MVC simples sem camada de visao
- entrada da aplicacao: API HTTP
- biblioteca TTS: `MaryTTS`
- modo TTS inicial: embutido no app
- sotaque inicial: `en_US`
- validacao lexical: API externa

## Impacto da decisao

- `reader-app` fica responsavel apenas pela camada HTTP
- `reader-core` concentra a regra de validacao e decisao de leitura
- `reader-integration` concentra dependencias externas
- a pronuncia so deve ocorrer depois da confirmacao de que a palavra existe em ingles
- o primeiro recorte da API e propositalmente pequeno para reduzir acoplamento inicial
