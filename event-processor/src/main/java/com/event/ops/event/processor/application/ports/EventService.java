package com.event.ops.event.processor.application.ports;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventService {
    void processEvent(String message) throws JsonProcessingException;
}
