package com.event_ops.event_processor.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Instant timestamp;
    private final List<String> details;
    private final List<String> stackTrace;

    public ErrorResponse(int status, String message, List<String> details, List<String> stackTrace) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
        this.details = details;
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(int status, String message, List<String> details) {
        this(status, message, details, null);
    }
}
