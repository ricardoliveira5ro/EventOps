package com.event.ops.search.infrastructure.web.controller;

import com.event.ops.search.application.ports.SearchService;
import com.event.ops.search.infrastructure.web.dto.CountResponse;
import com.event.ops.search.infrastructure.web.dto.DailyResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class SearchController {

    private final SearchService searchService;
    private final MeterRegistry meterRegistry;

    @Autowired
    public SearchController(SearchService searchService, MeterRegistry meterRegistry) {
        this.searchService = searchService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponse> getCount(@RequestParam(value = "eventName", required = false) String eventName) {
        Counter counter = meterRegistry.counter("search_event_count_requests_total");
        counter.increment();

        Timer timer = meterRegistry.timer("search_event_count_latency_seconds");

        return timer.record(() -> {
            Long count = searchService.eventCount(eventName);
            CountResponse countResponse = new CountResponse(count);

            return ResponseEntity.ok(countResponse);
        });
    }

    @GetMapping("/daily")
    public ResponseEntity<List<DailyResponse>> getDailyAggregate(@RequestParam(value = "eventName", required = false) String eventName) {
        Counter counter = meterRegistry.counter("search_daily_aggregate_requests_total");
        counter.increment();

        Timer timer = meterRegistry.timer("search_daily_aggregate_latency_seconds");

        return timer.record(() -> ResponseEntity.ok(searchService.dailyAggregate(eventName)));
    }
}
