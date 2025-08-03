package com.event.ops.search.application.ports;

import com.event.ops.search.infrastructure.web.dto.DailyResponse;
import java.util.List;

public interface SearchService {
    Long eventCount(String eventName);
    List<DailyResponse> dailyAggregate(String eventName);
}
