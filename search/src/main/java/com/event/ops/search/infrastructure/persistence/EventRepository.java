package com.event.ops.search.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Long countByEventName(String eventName);
}
