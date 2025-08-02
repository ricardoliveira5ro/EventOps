package com.event.ops.event.processor.infrastructure.persistence;

import com.event.ops.database.converter.EventMetaDataConverter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EventMetaDataConverterTest {
    private final EventMetaDataConverter converter = new EventMetaDataConverter();

    @Test
    void convertToDatabaseColumn_validMap_returnsJsonString() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("email", "test@example.com");
        metadata.put("signupSource", "mobile");

        String json = converter.convertToDatabaseColumn(metadata);

        assertTrue(json.contains("email"));
        assertTrue(json.contains("signupSource"));
    }

    @Test
    void convertToDatabaseColumn_nullMap_returnsNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void convertToDatabaseColumn_invalidMap_throwsException() {
        Map<String, Object> badMap = new HashMap<>() {
            {
                put("bad", new Object() {}); // non-serializable object
            }
        };

        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToDatabaseColumn(badMap);
        });
    }

    @Test
    void convertToEntityAttribute_validJson_returnsMap() {
        String json = "{\"email\":\"test@example.com\",\"signupSource\":\"mobile\"}";

        Map<String, Object> result = converter.convertToEntityAttribute(json);

        assertEquals("test@example.com", result.get("email"));
        assertEquals("mobile", result.get("signupSource"));
    }

    @Test
    void convertToEntityAttribute_nullJson_returnsNull() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void convertToEntityAttribute_invalidJson_throwsException() {
        String invalidJson = "{\"email\":\"test@example.com\",,}";

        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToEntityAttribute(invalidJson);
        });
    }
}
