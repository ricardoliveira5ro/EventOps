package com.event.ops.event.collector.infrastructure.web.controller;

import com.event.ops.auth.application.service.CurrentClientService;
import com.event.ops.event.collector.application.service.EventServiceImpl;
import com.event.ops.event.collector.infrastructure.web.dto.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventServiceImpl eventService;

    @MockitoBean
    private CurrentClientService currentClientService;

    private EventRequest request = new EventRequest();

    @BeforeEach
    void setUp() {
        request = new EventRequest();
        request.setName("user_registered");
        request.setClientKey("tester-key");
        request.setTimestamp(Instant.now());
        request.setMetadata(Map.of("email", "tester@example.com",
                "referralCode", "REF123",
                "signupSource", "mobile"));
    }

    @Test
    public void processEvent() throws Exception {
        when(currentClientService.getClientKey()).thenReturn(request.getClientKey());

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Event received"));

        verify(eventService).pushEvent(any(EventRequest.class));
    }

    @Test
    public void processEvent_throwsJsonProcessingException() throws Exception {
        doThrow(new JsonProcessingException("Serialization failed") {}).when(eventService)
                .pushEvent(any(EventRequest.class));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void processEvent_InvalidEventRequestThrowsException() throws Exception {
        request = new EventRequest();

        eventService.pushEvent(any(EventRequest.class));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }
}
