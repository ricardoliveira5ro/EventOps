package com.event.ops.search.infrastructure.web.controller;

import com.event.ops.search.application.ports.SearchService;
import com.event.ops.search.infrastructure.web.dto.CountResponse;
import com.event.ops.search.infrastructure.web.dto.DailyResponse;
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

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponse> getCount(@RequestParam(value = "eventName", required = false) String eventName) {
        Long count = searchService.eventCount(eventName);
        CountResponse countResponse = new CountResponse(count);

        return ResponseEntity.ok(countResponse);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<DailyResponse>> getDailyAggregate(@RequestParam(value = "eventName", required = false) String eventName) {
        return ResponseEntity.ok(searchService.dailyAggregate(eventName));
    }
}
