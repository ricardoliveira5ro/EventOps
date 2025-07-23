package com.event_ops.event_collector.service;

import com.event_ops.event_collector.dto.EventRequest;
import com.event_ops.event_collector.model.Event;
import com.event_ops.event_collector.producer.EventProducer;
import com.event_ops.event_collector.repository.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventProducer eventProducer, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.eventProducer = eventProducer;
        this.objectMapper = objectMapper;
    }

    public void processEvent(EventRequest eventRequest) {
        Event event = new Event();

        event.setEventName(eventRequest.getName());
        event.setTimestamp(eventRequest.getTimestamp());
        event.setUserId(eventRequest.getUserId());
        event.setMetadata(eventRequest.getMetadata());

        Event savedEvent = eventRepository.save(event);

        try {
            String eventJson = objectMapper.writeValueAsString(savedEvent);
            eventProducer.sendMessage("event-processor", objectMapper.writeValueAsString(event));

        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
    }
}
