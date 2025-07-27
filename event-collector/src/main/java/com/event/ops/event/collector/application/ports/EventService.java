package com.event.ops.event.collector.application.ports;

import com.event.ops.event.collector.infrastructure.web.dto.EventRequest;

public interface EventService {
    void pushEvent(EventRequest eventRequest);
}
