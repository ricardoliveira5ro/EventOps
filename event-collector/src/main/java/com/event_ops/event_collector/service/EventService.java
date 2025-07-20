package com.event_ops.event_collector.service;

import com.event_ops.event_collector.dto.EventRequest;
import com.event_ops.event_collector.model.Event;
import com.event_ops.event_collector.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void processEvent(EventRequest eventRequest) {
        Event event = new Event();

        event.setEventName(eventRequest.getName());
        event.setTimestamp(eventRequest.getTimestamp());
        event.setUserId(eventRequest.getUserId());
        event.setMetadata(eventRequest.getMetadata());

        eventRepository.save(event);
    }
}
