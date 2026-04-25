# Endpoints Iniciais

## Direcao

O projeto vai expor uma API HTTP simples, sem camada de visao.

Os endpoints abaixo representam apenas a primeira estruturacao da aplicacao. Nenhum controller foi implementado ainda.

## Endpoints planejados

### `POST /api/words/validate`

Objetivo:

- receber uma palavra
- verificar se ela existe em ingles
- retornar se esta apta para leitura

### `POST /api/words/read`

Objetivo:

- receber uma palavra
- validar existencia em ingles
- solicitar leitura usando `MaryTTS`
- usar apenas o sotaque americano inicial

### `GET /api/voice`

Objetivo:

- retornar a configuracao de voz ativa
- expor que o locale inicial e `en_US`

### `GET /api/health`

Objetivo:

- verificar se a API esta no ar
- servir como endpoint tecnico simples
