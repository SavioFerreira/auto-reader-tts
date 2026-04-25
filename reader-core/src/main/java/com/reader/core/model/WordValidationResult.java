package com.reader.core.model;

public record WordValidationResult(
        String originalWord,
        String normalizedWord,
        boolean existsInEnglish,
        boolean readyToRead
) {
}

