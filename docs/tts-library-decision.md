# Decisao de Biblioteca TTS

## Biblioteca escolhida

`MaryTTS`

Repositorio principal:

- https://github.com/marytts/marytts

## Motivos da escolha

1. E escrita em Java e open source, alinhada com a restricao do projeto.
2. Tem alta visibilidade no GitHub dentro do nicho Java TTS.
3. Possui suporte oficial a multiplos idiomas, incluindo ingles americano e britanico no ecossistema do projeto.
4. Permite uso embarcado em projeto Java ou por meio de servidor local da propria plataforma.
5. Tem artefatos Maven publicados para a linha 5.2.1.

## Alternativas descartadas

### FreeTTS

Nao foi escolhida como base principal porque, embora seja historica e escrita em Java, e mais limitada em qualidade e flexibilidade para o requisito de sotaques.

## Como vamos usar no projeto

No curto prazo, o modulo `reader-integration` sera preparado para integrar com o runtime embarcado do `MaryTTS`.

Base inicial definida:

- `de.dfki.mary:marytts-runtime:5.2.1`
- `de.dfki.mary:marytts-lang-en:5.2.1`
- `de.dfki.mary:voice-cmu-slt-hsmm:5.2.1`

Escopo inicial de sotaque:

- somente ingles americano (`en_US`)
- voz inicial `cmu-slt-hsmm`

## Observacao de build

Algumas dependencias transitivas do ecossistema MaryTTS 5.2.1 nao estao totalmente concentradas no Maven Central. Por isso, o projeto foi configurado tambem com repositórios auxiliares para resolver o build com Maven.

## Risco conhecido

Embora MaryTTS seja a melhor escolha para o nosso criterio atual de Java open source madura e conhecida, o modo embarcado com `marytts-runtime` e pacotes de voz exige dependencias e repositórios auxiliares fora do fluxo mais simples do Maven Central. Isso precisa continuar sob validacao conforme o projeto avancar.
