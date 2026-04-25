package com.reader.core.validation;

import com.reader.core.exception.InvalidWordException;

import java.util.Locale;
import java.util.regex.Pattern;

public class WordInputValidator {

    private static final Pattern WORD_PATTERN = Pattern.compile("^[a-zA-Z]+(?:['-][a-zA-Z]+)*$");

    public String normalize(String word) {
        if (word == null) {
            throw new InvalidWordException("The word must be provided.");
        }

        String normalized = word.trim().toLowerCase(Locale.ROOT);
        if (normalized.isBlank()) {
            throw new InvalidWordException("The word must not be blank.");
        }

        if (!WORD_PATTERN.matcher(normalized).matches()) {
            throw new InvalidWordException("The word must contain only English letters, apostrophes or hyphens.");
        }

        return normalized;
    }
}

