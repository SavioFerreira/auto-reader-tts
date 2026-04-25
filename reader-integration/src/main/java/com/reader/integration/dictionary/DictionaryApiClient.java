package com.reader.integration.dictionary;

import com.reader.core.exception.DictionaryUnavailableException;
import com.reader.core.ports.DictionaryGateway;
import com.reader.integration.config.DictionaryApiSettings;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DictionaryApiClient implements DictionaryGateway {

    private final HttpClient httpClient;
    private final DictionaryApiSettings settings;

    public DictionaryApiClient(DictionaryApiSettings settings) {
        this.settings = settings;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(settings.timeout())
                .build();
    }

    @Override
    public boolean existsInEnglish(String normalizedWord) {
        HttpRequest request = HttpRequest.newBuilder(buildLookupUri(normalizedWord))
                .timeout(settings.timeout())
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() == 200) {
                return true;
            }
            if (response.statusCode() == 404) {
                return false;
            }

            throw new DictionaryUnavailableException(
                    "Dictionary API returned unexpected status %d.".formatted(response.statusCode())
            );
        } catch (IOException exception) {
            throw new DictionaryUnavailableException("Failed to call the external dictionary API.", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new DictionaryUnavailableException("Dictionary lookup was interrupted.", exception);
        }
    }

    private URI buildLookupUri(String normalizedWord) {
        String encodedWord = URLEncoder.encode(normalizedWord, StandardCharsets.UTF_8);
        return settings.baseUri().resolve(encodedWord);
    }
}
