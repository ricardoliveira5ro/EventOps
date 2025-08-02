package com.event.ops.event.processor.application.service;

import com.event.ops.database.entity.EventEntity;
import com.event.ops.event.processor.infrastructure.persistence.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EventRepository eventRepository;

    private String message;
    private EventEntity eventEntity;

    @BeforeEach
    void setup() {
        Instant timestamp = Instant.now();
        message = String.format("{\"eventName\":\"user_registered\",\"timestamp\":\"%s\",\"userId\":\"tester\"," +
                "\"metadata\":{\"email\":\"tester@example.com\",\"referralCode\":\"REF123\",\"signupSource\":\"mobile\"}}", timestamp);

        eventEntity = new EventEntity();
        eventEntity.setId(UUID.randomUUID());
        eventEntity.setEventName("user_registered");
        eventEntity.setUserId("tester");
        eventEntity.setTimestamp(Instant.now());
        eventEntity.setMetadata(Map.of("email", "tester@example.com",
                "referralCode", "REF123",
                "signupSource", "mobile"));
    }

    @Test
    void processEvent() throws JsonProcessingException {
        when(objectMapper.readValue(message, EventEntity.class)).thenReturn(eventEntity);

        eventService.processEvent(message);

        verify(eventRepository).save(eventEntity);
    }

    @Test
    void processEvent_invalidMessage() throws Exception {
        when(objectMapper.readValue(anyString(), eq(EventEntity.class)))
                .thenThrow(new JsonProcessingException("JSON parsing failed") {});

        assertThrows(JsonProcessingException.class, () -> eventService.processEvent("INVALID_JSON"));

        verify(eventRepository, never()).save(any());
    }
}
