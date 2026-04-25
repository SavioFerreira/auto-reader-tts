package com.reader.app.dto.response;

public record ErrorResponse(
        String code,
        String message
) {
}

