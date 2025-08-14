package com.event.ops.event.processor.application.service;

import com.event.ops.auth.application.service.CurrentClientService;
import com.event.ops.database.entity.EventEntity;
import com.event.ops.event.processor.application.ports.EventService;
import com.event.ops.event.processor.domain.model.Event;
import com.event.ops.event.processor.infrastructure.persistence.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CurrentClientService currentClientService;
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    private final Counter counter;
    private final Timer timer;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, CurrentClientService currentClientService, ObjectMapper objectMapper, CacheManager cacheManager, MeterRegistry meterRegistry) {
        this.eventRepository = eventRepository;
        this.currentClientService = currentClientService;
        this.objectMapper = objectMapper;
        this.cacheManager = cacheManager;

        this.counter = meterRegistry.counter("event.processor.consumed");
        this.timer = meterRegistry.timer("event.processor.latency");
    }

    @Override
    public void processEvent(String message) throws JsonProcessingException {
        counter.increment();

        Timer.Sample sample = Timer.start();
        try {
            Event event = objectMapper.readValue(message, Event.class);

            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventName(event.getEventName());
            eventEntity.setMetadata(event.getMetadata());
            eventEntity.setTimestamp(event.getTimestamp());
            eventEntity.setClient(currentClientService.getCurrentClient(event.getClientKey()));

            eventRepository.save(eventEntity);

            // Using cacheManager instead of @CacheEvict because the key -> 'event' does not exist before running
            Objects.requireNonNull(cacheManager.getCache("dailyAggregate")).evict(eventEntity.getEventName());

            log.info("Event saved successfully");
        } finally {
            sample.stop(timer);
        }
    }
}
