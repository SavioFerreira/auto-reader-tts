package com.reader.app.exception;

import com.reader.app.dto.response.ErrorResponse;
import com.reader.core.exception.DictionaryUnavailableException;
import com.reader.core.exception.InvalidWordException;
import com.reader.core.exception.SpeechSynthesisException;
import com.reader.core.exception.WordNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidWordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWord(InvalidWordException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("INVALID_WORD", exception.getMessage()));
    }

    @ExceptionHandler(WordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordNotFound(WordNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse("WORD_NOT_FOUND", exception.getMessage()));
    }

    @ExceptionHandler({
            DictionaryUnavailableException.class,
            SpeechSynthesisException.class
    })
    public ResponseEntity<ErrorResponse> handleUpstream(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse("UPSTREAM_FAILURE", exception.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleValidation(Exception exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("REQUEST_VALIDATION_FAILED", exception.getMessage()));
    }
}

