package com.event_ops.event_collector.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = "event-processor", groupId = "event-processor-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
