package com.reader.core.exception;

public class DictionaryUnavailableException extends RuntimeException {

    public DictionaryUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictionaryUnavailableException(String message) {
        super(message);
    }
}

