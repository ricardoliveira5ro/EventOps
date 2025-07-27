package com.event.ops.event.processor.infrastructure.kafka;

import com.event.ops.event.processor.application.service.EventServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    private final EventServiceImpl eventService;

    public EventConsumer(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @KafkaListener(topics = "event-processor", groupId = "event-processor-group")
    public void listen(String message) {
        eventService.processEvent(message);
    }
}
