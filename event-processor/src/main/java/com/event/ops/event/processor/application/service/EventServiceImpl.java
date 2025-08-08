package com.event.ops.event.processor.application.service;

import com.event.ops.database.entity.EventEntity;
import com.event.ops.event.processor.application.ports.EventService;
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
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    private final Counter counter;
    private final Timer timer;
    private final MeterRegistry meterRegistry;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ObjectMapper objectMapper, CacheManager cacheManager, MeterRegistry meterRegistry) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
        this.cacheManager = cacheManager;

        this.meterRegistry = meterRegistry;
        this.counter = meterRegistry.counter("event.processor.consumed");
        this.timer = meterRegistry.timer("event.processor.latency");
    }

    @Override
    public void processEvent(String message) throws JsonProcessingException {
        counter.increment();

        Timer.Sample sample = Timer.start();
        try {
            EventEntity event = objectMapper.readValue(message, EventEntity.class);
            eventRepository.save(event);

            // Using cacheManager instead of @CacheEvict because the key -> 'event' does not exist before running
            Objects.requireNonNull(cacheManager.getCache("dailyAggregate")).evict(event.getEventName());

            log.info("Event saved successfully");
        } finally {
            sample.stop(timer);
        }
    }
}
