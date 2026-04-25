package com.reader.app.dto.response;

public record WordValidationResponse(
        String originalWord,
        String normalizedWord,
        String readableText,
        boolean existsInEnglish,
        boolean readyToRead
) {
}
