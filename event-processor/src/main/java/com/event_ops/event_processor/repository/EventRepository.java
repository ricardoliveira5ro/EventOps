package com.event_ops.event_processor.repository;

import com.event_ops.event_processor.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String> {

}
