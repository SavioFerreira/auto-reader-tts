package com.reader.core.service;

import com.reader.core.exception.WordNotFoundException;
import com.reader.core.model.VoiceConfiguration;
import com.reader.core.model.WordReadResult;
import com.reader.core.model.WordValidationResult;
import com.reader.core.ports.DictionaryGateway;
import com.reader.core.ports.SpeechGateway;
import com.reader.core.validation.WordInputValidator;

import java.util.ArrayList;
import java.util.List;

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
        List<String> normalizedWords = wordInputValidator.normalizeWords(originalWord);
        List<String> readableWords = new ArrayList<>();
        boolean existsInEnglish = true;

        for (String normalizedWord : normalizedWords) {
            if (dictionaryGateway.existsInEnglish(normalizedWord)) {
                readableWords.add(normalizedWord);
                continue;
            }

            existsInEnglish = false;
        }

        String normalizedText = String.join(" ", normalizedWords);
        String readableText = String.join(" ", readableWords);

        return new WordValidationResult(
                originalWord,
                normalizedText,
                readableText,
                existsInEnglish,
                !readableWords.isEmpty()
        );
    }

    public WordReadResult readWord(String originalWord) {
        WordValidationResult validationResult = validateWord(originalWord);
        if (!validationResult.readyToRead()) {
            throw new WordNotFoundException(
                    "None of the words in '%s' were found in the configured English dictionary."
                            .formatted(validationResult.normalizedWord())
            );
        }

        byte[] audioBytes = speechGateway.synthesize(validationResult.readableText());
        return new WordReadResult(
                validationResult.originalWord(),
                validationResult.normalizedWord(),
                validationResult.readableText(),
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
