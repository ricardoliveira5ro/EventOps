package com.event.ops.common.exception;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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

    private final MeterRegistry meterRegistry;

    public GlobalExceptionHandler(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        countErrorMetric(ex);

        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed", details, ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptions(UnauthorizedException ex) {
        countErrorMetric(ex);

        List<String> details = List.of(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Not authorized to perform this action", details, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        countErrorMetric(ex);

        List<String> details = List.of(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "An unexpected error occurred", details, ex);
    }

    // ----------------------------------------------------------------------------------------------------------------- //

    private void countErrorMetric(Exception ex) {
        Counter.builder("service.errors")
                .tag("service", applicationName)
                .tag("exception", ex.getClass().getSimpleName())
                .register(meterRegistry)
                .increment();
    }

    private List<String> buildStackTrace(Exception ex) {
        if ("dev".equalsIgnoreCase(activeProfile)) {
            return Arrays.stream(ex.getStackTrace())
                    .map(StackTraceElement::toString)
                    .toList();
        }
        return null;
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, List<String> details, Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(status.value(), message, details, buildStackTrace(ex)), status);
    }
}
