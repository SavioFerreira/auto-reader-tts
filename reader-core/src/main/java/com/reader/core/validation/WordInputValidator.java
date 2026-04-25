package com.reader.core.validation;

import com.reader.core.exception.InvalidWordException;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class WordInputValidator {

    private static final Pattern WORD_PATTERN = Pattern.compile("^[a-zA-Z]+(?:['-][a-zA-Z]+)*$");

    public String normalize(String text) {
        if (text == null) {
            throw new InvalidWordException("The word must be provided.");
        }

        String normalized = text.trim().toLowerCase(Locale.ROOT);
        if (normalized.isBlank()) {
            throw new InvalidWordException("The word must not be blank.");
        }

        if (hasUnsupportedSpacing(normalized)) {
            throw new InvalidWordException("Words must be separated by a single space.");
        }

        for (String word : normalized.split(" ")) {
            if (!WORD_PATTERN.matcher(word).matches()) {
                throw new InvalidWordException(
                        "The text must contain only English letters, apostrophes or hyphens."
                );
            }
        }

        return normalized;
    }

    public List<String> normalizeWords(String text) {
        return List.of(normalize(text).split(" "));
    }

    private boolean hasUnsupportedSpacing(String normalized) {
        if (normalized.contains("  ")) {
            return true;
        }

        return normalized.chars()
                .anyMatch(character -> Character.isWhitespace(character) && character != ' ');
    }
}
