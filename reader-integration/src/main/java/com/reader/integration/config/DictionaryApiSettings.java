package com.reader.integration.config;

import java.net.URI;
import java.time.Duration;

public record DictionaryApiSettings(
        URI baseUri,
        Duration timeout
) {
}

