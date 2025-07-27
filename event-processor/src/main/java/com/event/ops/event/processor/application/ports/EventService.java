package com.event.ops.event.processor.application.ports;

public interface EventService {
    void processEvent(String message);
}
