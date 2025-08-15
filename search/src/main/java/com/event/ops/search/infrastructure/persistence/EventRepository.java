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
    Long countByClientIdAndEventName(UUID clientId, String eventName);

    Long countByClient_Id(UUID clientId);

    @Query(value = "SELECT event_date AS date, total FROM V_DAILY_EVENT_COUNT " +
                        "WHERE (:eventName IS NULL OR event_name = :eventName) AND client_id = :clientId " +
                        "ORDER BY event_date DESC", nativeQuery = true)
    List<IEventCount> aggregateByDateAndEventName(@Param("eventName") String eventName, @Param("clientId") UUID clientId);
}
