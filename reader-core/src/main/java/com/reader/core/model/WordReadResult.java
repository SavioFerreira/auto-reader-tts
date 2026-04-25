package com.reader.core.model;

public record WordReadResult(
        String originalWord,
        String normalizedWord,
        boolean existsInEnglish,
        boolean readyToRead,
        String contentType,
        byte[] audioBytes,
        VoiceConfiguration voiceConfiguration
) {
}

