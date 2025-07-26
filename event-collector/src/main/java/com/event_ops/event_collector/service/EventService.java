package com.event_ops.event_collector.service;

import com.event_ops.event_collector.dto.EventRequest;
import com.event_ops.event_collector.model.Event;
import com.event_ops.event_collector.producer.EventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventService {

    private final EventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventService(EventProducer eventProducer, ObjectMapper objectMapper) {
        this.eventProducer = eventProducer;
        this.objectMapper = objectMapper;
    }

    public void pushEvent(EventRequest eventRequest) {
        Event event = new Event();

        event.setEventName(eventRequest.getName());
        event.setTimestamp(eventRequest.getTimestamp());
        event.setUserId(eventRequest.getUserId());
        event.setMetadata(eventRequest.getMetadata());

        try {
            eventProducer.sendMessage("event-processor", objectMapper.writeValueAsString(event));
            log.info("Event sent to topic 'event-processor': {}", event);

        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
