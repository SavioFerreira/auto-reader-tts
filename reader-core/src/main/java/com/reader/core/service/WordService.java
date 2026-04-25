package com.reader.core.service;

import com.reader.core.exception.WordNotFoundException;
import com.reader.core.model.VoiceConfiguration;
import com.reader.core.model.WordReadResult;
import com.reader.core.model.WordValidationResult;
import com.reader.core.ports.DictionaryGateway;
import com.reader.core.ports.SpeechGateway;
import com.reader.core.validation.WordInputValidator;

public class WordService {

    private final DictionaryGateway dictionaryGateway;
    private final SpeechGateway speechGateway;
    private final WordInputValidator wordInputValidator;

    public WordService(
            DictionaryGateway dictionaryGateway,
            SpeechGateway speechGateway,
            WordInputValidator wordInputValidator
    ) {
        this.dictionaryGateway = dictionaryGateway;
        this.speechGateway = speechGateway;
        this.wordInputValidator = wordInputValidator;
    }

    public WordValidationResult validateWord(String originalWord) {
        String normalizedWord = wordInputValidator.normalize(originalWord);
        boolean existsInEnglish = dictionaryGateway.existsInEnglish(normalizedWord);

        return new WordValidationResult(
                originalWord,
                normalizedWord,
                existsInEnglish,
                existsInEnglish
        );
    }

    public WordReadResult readWord(String originalWord) {
        WordValidationResult validationResult = validateWord(originalWord);
        if (!validationResult.readyToRead()) {
            throw new WordNotFoundException(
                    "The word '%s' was not found in the configured English dictionary."
                            .formatted(validationResult.normalizedWord())
            );
        }

        byte[] audioBytes = speechGateway.synthesize(validationResult.normalizedWord());
        return new WordReadResult(
                validationResult.originalWord(),
                validationResult.normalizedWord(),
                validationResult.existsInEnglish(),
                validationResult.readyToRead(),
                speechGateway.getAudioContentType(),
                audioBytes,
                speechGateway.getVoiceConfiguration()
        );
    }

    public VoiceConfiguration getVoiceConfiguration() {
        return speechGateway.getVoiceConfiguration();
    }
}
