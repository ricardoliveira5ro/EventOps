package com.event.ops.event.processor.consumer;

import com.event.ops.event.processor.service.EventService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    private final EventService eventService;

    public EventConsumer(EventService eventService) {
        this.eventService = eventService;
    }

    @KafkaListener(topics = "event-processor", groupId = "event-processor-group")
    public void listen(String message) {
        eventService.processEvent(message);
    }
}
