package com.event.ops.search.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyResponse {
    private String date;
    private Long count;
}
