package com.event.ops.event.collector.infrastructure.web.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .toList();

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation failed", details), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        List<String> details = List.of(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        List<String> stackTrace = null;
        if ("dev".equalsIgnoreCase(activeProfile)) {
            stackTrace = Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
        }

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "An unexpected error occurred", details, stackTrace), HttpStatus.BAD_REQUEST);
    }
}
