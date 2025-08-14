package com.event.ops.event.processor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private String eventName;
    private Map<String, Object> metadata;
    private Instant timestamp;
    private String clientKey;
}
