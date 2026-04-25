package com.reader.app.dto.response;

public record WordReadResponse(
        String originalWord,
        String normalizedWord,
        boolean existsInEnglish,
        boolean readyToRead,
        String locale,
        String voice,
        String contentType,
        String audioBase64
) {
}

