package com.reader.core.model;

public record WordValidationResult(
        String originalWord,
        String normalizedWord,
        String readableText,
        boolean existsInEnglish,
        boolean readyToRead
) {
}
