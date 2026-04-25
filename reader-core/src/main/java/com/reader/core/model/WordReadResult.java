package com.reader.core.model;

public record WordReadResult(
        String originalWord,
        String normalizedWord,
        String readableText,
        boolean existsInEnglish,
        boolean readyToRead,
        String contentType,
        byte[] audioBytes,
        VoiceConfiguration voiceConfiguration
) {
}
