package com.reader.core.model;

public record VoiceConfiguration(
        String locale,
        String voice,
        boolean embedded
) {
}

