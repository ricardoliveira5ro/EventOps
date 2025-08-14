package com.event.ops.event.collector.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class EventRequest {

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private Map<String, Object> metadata;

    @NotNull
    private Instant timestamp;

    private String clientKey;

    @Override
    public String toString() {
        return "EventRequest{" +
                "name='" + name + '\'' +
                ", metadata=" + metadata +
                ", timestamp=" + timestamp +
                ", clientKey='" + clientKey + '\'' +
                '}';
    }
}
