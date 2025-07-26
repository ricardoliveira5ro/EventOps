package com.event.ops.event.collector.dto;

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

    @NotBlank
    @NotNull
    private String userId;

    @NotNull
    private Map<String, Object> metadata;

    @NotNull
    private Instant timestamp;

    @Override
    public String toString() {
        return "EventRequest{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", metadata=" + metadata +
                ", timestamp=" + timestamp +
                '}';
    }
}
