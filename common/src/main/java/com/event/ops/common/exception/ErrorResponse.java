package com.event.ops.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private final Date timestamp;
    private final List<String> details;
    private final List<String> stackTrace;

    public ErrorResponse(int status, String message, List<String> details, List<String> stackTrace) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
        this.details = details;
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(int status, String message, List<String> details) {
        this(status, message, details, null);
    }
}
