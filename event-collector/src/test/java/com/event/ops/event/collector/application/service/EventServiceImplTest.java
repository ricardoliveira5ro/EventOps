package com.event.ops.event.collector.application.service;

import com.event.ops.event.collector.domain.model.Event;
import com.event.ops.event.collector.infrastructure.kafka.EventProducer;
import com.event.ops.event.collector.infrastructure.web.dto.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceImplTest {

    @Autowired
    private EventServiceImpl eventService;

    @MockitoBean
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventProducer eventProducer;

    @Test
    void pushEventToKafkaTopic() throws JsonProcessingException {
        Instant timestamp = Instant.now();

        EventRequest request = new EventRequest();
        request.setName("user_registered");
        request.setUserId("tester");
        request.setTimestamp(timestamp);
        request.setMetadata(Map.of("email", "tester@example.com",
                                    "referralCode", "REF123",
                                "signupSource", "mobile"));

        String expected = String.format("{\"eventName\":\"user_registered\",\"timestamp\":\"%s\",\"userId\":\"tester\"," +
                "\"metadata\":{\"email\":\"tester@example.com\",\"referralCode\":\"REF123\",\"signupSource\":\"mobile\"}}", timestamp);

        when(objectMapper.writeValueAsString(any(Event.class))).thenReturn(expected);

        eventService.pushEvent(request);

        verify(eventProducer).sendMessage("event-processor", expected);
    }

    @Test
    void pushEventToKafkaTopic_throwsJsonProcessingException() throws Exception {
        EventRequest request = new EventRequest();
        request.setName("user_registered");

        when(objectMapper.writeValueAsString(any(Event.class)))
                .thenThrow(new JsonProcessingException("Serialization failed") {});

        assertThrows(JsonProcessingException.class, () -> {
            eventService.pushEvent(request);
        });

        verify(eventProducer, never()).sendMessage(any(), any());
    }
}
