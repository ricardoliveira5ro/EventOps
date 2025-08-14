package com.event.ops.event.collector.infrastructure.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class EventProducerTest {

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    private EventProducer eventProducer;

    @BeforeEach
    void setUp() {
        eventProducer = new EventProducer(kafkaTemplate);
    }

    @Test
    void sendMessage() {
        String topic = "mock-topic";
        String message = "{\"eventName\":\"user_registered\",\"timestamp\":\"%s\"," +
                "\"metadata\":{\"email\":\"tester@example.com\",\"referralCode\":\"REF123\",\"signupSource\":\"mobile\"}},\"clientKey\":\"tester-key\"";

        eventProducer.sendMessage(topic, message);

        verify(kafkaTemplate).send(topic, message);
    }
}
