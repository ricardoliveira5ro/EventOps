package com.event.ops.event.processor.infrastructure.kafka;

import com.event.ops.event.processor.application.service.EventServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventConsumerTest {

    @Mock
    private EventServiceImpl eventService;

    @InjectMocks
    private EventConsumer eventConsumer;

    @Test
    void listen() throws JsonProcessingException {
        Instant timestamp = Instant.now();
        String message = String.format("{\"eventName\":\"user_registered\",\"timestamp\":\"%s\",\"userId\":\"tester\"," +
                "\"metadata\":{\"email\":\"tester@example.com\",\"referralCode\":\"REF123\",\"signupSource\":\"mobile\"}}", timestamp);

        eventConsumer.listen(message);

        verify(eventService).processEvent(message);
    }
}
