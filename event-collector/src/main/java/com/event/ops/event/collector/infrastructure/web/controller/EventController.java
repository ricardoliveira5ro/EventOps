package com.event.ops.event.collector.infrastructure.web.controller;

import com.event.ops.event.collector.application.service.EventServiceImpl;
import com.event.ops.event.collector.infrastructure.web.dto.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> processEvent(@Valid @RequestBody EventRequest eventRequest) throws JsonProcessingException {
        eventService.pushEvent(eventRequest);

        return ResponseEntity.ok("Event received");
    }
}
