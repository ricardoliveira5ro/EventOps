package com.event_ops.event_processor.service;

import com.event_ops.event_processor.model.EventEntity;
import com.event_ops.event_processor.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventService(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    public void processEvent(String message) {
        try {
            EventEntity event = objectMapper.readValue(message, EventEntity.class);
            eventRepository.save(event);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
