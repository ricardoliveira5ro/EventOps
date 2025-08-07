package com.event.ops.event.collector.infrastructure.web.controller;

import com.event.ops.event.collector.application.service.EventServiceImpl;
import com.event.ops.event.collector.infrastructure.web.dto.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventServiceImpl eventService;
    private final Counter counter;
    private final Timer timer;

    @Autowired
    public EventController(EventServiceImpl eventService, MeterRegistry meterRegistry) {
        this.eventService = eventService;
        this.counter = meterRegistry.counter("event.collector.requests");
        this.timer = meterRegistry.timer("event.collector.latency");
    }

    @PostMapping
    public ResponseEntity<String> processEvent(@Valid @RequestBody EventRequest eventRequest) throws JsonProcessingException {
        counter.increment();

        return timer.record(() -> {
            try {
                eventService.pushEvent(eventRequest);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok("Event received");
        });
    }
}
