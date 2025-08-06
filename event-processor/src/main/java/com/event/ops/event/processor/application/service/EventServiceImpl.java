package com.event.ops.event.processor.application.service;

import com.event.ops.database.entity.EventEntity;
import com.event.ops.event.processor.application.ports.EventService;
import com.event.ops.event.processor.infrastructure.persistence.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ObjectMapper objectMapper, CacheManager cacheManager) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
        this.cacheManager = cacheManager;
    }

    @Override
    public void processEvent(String message) throws JsonProcessingException {
        EventEntity event = objectMapper.readValue(message, EventEntity.class);
        eventRepository.save(event);

        // Using cacheManager instead of @CacheEvict because the key -> 'event' does not exist before running
        Objects.requireNonNull(cacheManager.getCache("dailyAggregate")).evict(event.getEventName());

        log.info("Event saved successfully");
    }
}
