package com.event.ops.event.processor.application.service;

import com.event.ops.event.processor.application.ports.EventService;
import com.event.ops.event.processor.infrastructure.persistence.EventEntity;
import com.event.ops.event.processor.infrastructure.persistence.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void processEvent(String message) throws JsonProcessingException {
        EventEntity event = objectMapper.readValue(message, EventEntity.class);
        eventRepository.save(event);

        log.info("Event saved successfully");
    }
}
