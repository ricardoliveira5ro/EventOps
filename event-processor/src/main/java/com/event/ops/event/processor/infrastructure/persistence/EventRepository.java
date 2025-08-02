package com.event.ops.event.processor.infrastructure.persistence;

import com.event.ops.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

}
