package com.reader.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WordRequest(
        @NotBlank(message = "word must not be blank")
        String word
) {
}

