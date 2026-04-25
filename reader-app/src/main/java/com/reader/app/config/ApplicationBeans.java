package com.reader.app.config;

import com.reader.core.ports.DictionaryGateway;
import com.reader.core.ports.SpeechGateway;
import com.reader.core.service.WordService;
import com.reader.core.validation.WordInputValidator;
import com.reader.integration.config.DictionaryApiSettings;
import com.reader.integration.config.TtsSettings;
import com.reader.integration.dictionary.DictionaryApiClient;
import com.reader.integration.tts.MaryTtsSpeechGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.time.Duration;

@Configuration
public class ApplicationBeans {

    @Bean
    WordInputValidator wordInputValidator() {
        return new WordInputValidator();
    }

    @Bean
    DictionaryApiSettings dictionaryApiSettings(
            @Value("${reader.dictionary.base-url}") String baseUrl,
            @Value("${reader.dictionary.timeout}") Duration timeout
    ) {
        return new DictionaryApiSettings(URI.create(baseUrl), timeout);
    }

    @Bean
    TtsSettings ttsSettings(
            @Value("${reader.voice.locale}") String locale,
            @Value("${reader.voice.name}") String voice
    ) {
        return new TtsSettings(locale, voice);
    }

    @Bean
    DictionaryGateway dictionaryGateway(DictionaryApiSettings settings) {
        return new DictionaryApiClient(settings);
    }

    @Bean
    SpeechGateway speechGateway(TtsSettings settings) {
        return new MaryTtsSpeechGateway(settings);
    }

    @Bean
    WordService wordService(
            DictionaryGateway dictionaryGateway,
            SpeechGateway speechGateway,
            WordInputValidator wordInputValidator
    ) {
        return new WordService(dictionaryGateway, speechGateway, wordInputValidator);
    }
}

