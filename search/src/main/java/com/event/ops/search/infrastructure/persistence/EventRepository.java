package com.event.ops.search.infrastructure.persistence;

import com.event.ops.database.entity.EventEntity;
import com.event.ops.search.domain.model.IEventCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Long countByEventName(String eventName);

    @Query(value = "SELECT DATE(e.timestamp) AS date, COUNT(1) AS total " +
                    "FROM t_event e " +
                    "WHERE (:eventName IS NULL OR e.event_name = :eventName) " +
                    "GROUP BY DATE(e.timestamp) " +
                    "ORDER BY DATE(e.timestamp) DESC",
            nativeQuery = true)
    List<IEventCount> aggregateByDateAndEventName(@Param("eventName") String eventName);
}
