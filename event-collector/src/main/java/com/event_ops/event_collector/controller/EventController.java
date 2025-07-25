package com.event_ops.event_collector.controller;

import com.event_ops.event_collector.dto.EventRequest;
import com.event_ops.event_collector.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> processEvent(@Valid @RequestBody EventRequest eventRequest) {
        eventService.pushEvent(eventRequest);

        return ResponseEntity.ok("Event received");
    }
}
