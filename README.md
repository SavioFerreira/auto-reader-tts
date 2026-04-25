# Reader

Reader é uma aplicação Java com Spring Boot para validação e leitura de palavras em inglês com síntese de voz.

O app consulta um dicionário em inglês para verificar se a palavra informada existe e, quando válida, gera áudio usando MaryTTS com voz embarcada.

A API expõe endpoints para:

- verificar a saúde da aplicação;
- consultar a configuração de voz ativa;
- validar uma palavra em inglês;
- gerar a leitura da palavra em áudio.

O projeto é organizado em módulos separados para aplicação web, regras centrais e integrações externas.
