package com.reader.app.dto.response;

public record VoiceResponse(
        String locale,
        String voice,
        boolean embedded
) {
}

