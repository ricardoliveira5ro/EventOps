package com.event.ops.event.processor.application.service;

import com.event.ops.auth.application.service.CurrentClientService;
import com.event.ops.database.entity.ClientEntity;
import com.event.ops.database.entity.EventEntity;
import com.event.ops.event.processor.domain.model.Event;
import com.event.ops.event.processor.infrastructure.persistence.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CurrentClientService currentClientService;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private MeterRegistry meterRegistry;

    private EventServiceImpl eventService;
    private String message;
    private EventEntity eventEntity;

    @BeforeEach
    void setup() {
        Counter counter = mock(Counter.class);
        Timer timer = mock(Timer.class);

        when(meterRegistry.counter(anyString())).thenReturn(counter);
        when(meterRegistry.timer(anyString())).thenReturn(timer);

        when(cacheManager.getCache(anyString())).thenReturn(cache);

        eventService = new EventServiceImpl(eventRepository, currentClientService, objectMapper, cacheManager, meterRegistry);
    }

    @Test
    void processEvent() throws JsonProcessingException {
        String message = "{\"eventName\":\"user_registered\",\"timestamp\":\"2025-08-14T12:00:00Z\",\"clientKey\":\"tester-key\"}";

        Event event = new Event();
        event.setEventName("user_registered");
        event.setTimestamp(Instant.now());
        event.setClientKey("tester-key");

        when(objectMapper.readValue(anyString(), eq(Event.class))).thenReturn(event);

        eventService.processEvent(message);

        verify(eventRepository).save(any(EventEntity.class));
    }
}
