package com.event.ops.event.processor.infrastructure.persistence;

import com.event.ops.database.entity.EventEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void saveEvent() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName("user_registered");
        eventEntity.setUserId("tester");
        eventEntity.setTimestamp(Instant.now());
        eventEntity.setMetadata(Map.of("email", "tester@example.com",
                "referralCode", "REF123",
                "signupSource", "mobile"));

        eventRepository.save(eventEntity);

        Optional<EventEntity> result = eventRepository.findById(eventEntity.getId());

        assertTrue(result.isPresent());
        assertEquals("user_registered", result.get().getEventName());
    }
}
