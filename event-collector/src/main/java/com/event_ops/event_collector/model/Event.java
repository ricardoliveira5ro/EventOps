package com.event_ops.event_collector.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class Event {

    private String eventName;
    private Instant timestamp;
    private String userId;
    private Map<String, Object> metadata;
}
