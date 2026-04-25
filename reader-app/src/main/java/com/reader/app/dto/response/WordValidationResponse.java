package com.reader.app.dto.response;

public record WordValidationResponse(
        String originalWord,
        String normalizedWord,
        boolean existsInEnglish,
        boolean readyToRead
) {
}

