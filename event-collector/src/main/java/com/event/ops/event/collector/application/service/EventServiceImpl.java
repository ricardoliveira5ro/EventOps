package com.event.ops.event.collector.application.service;

import com.event.ops.event.collector.domain.model.Event;
import com.event.ops.event.collector.infrastructure.kafka.EventProducer;
import com.event.ops.event.collector.infrastructure.web.dto.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl {

    private final EventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventServiceImpl(EventProducer eventProducer, ObjectMapper objectMapper) {
        this.eventProducer = eventProducer;
        this.objectMapper = objectMapper;
    }

    public void pushEvent(EventRequest eventRequest) throws JsonProcessingException {
        Event event = new Event();

        event.setEventName(eventRequest.getName());
        event.setTimestamp(eventRequest.getTimestamp());
        event.setMetadata(eventRequest.getMetadata());
        event.setClientKey(eventRequest.getClientKey());

        eventProducer.sendMessage("processor", objectMapper.writeValueAsString(event));
        log.info("Event sent to topic 'processor': {}", event);
    }
}
