package com.event.ops.search.infrastructure.web.controller;

import com.event.ops.search.application.ports.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String,Long>> getCount(@RequestParam(value = "eventName", required = false) String eventName) {
        Long count = searchService.eventCount(eventName);

        Map<String,Long> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
}
