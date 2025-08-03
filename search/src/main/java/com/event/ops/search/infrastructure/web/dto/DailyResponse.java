package com.event.ops.search.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailyResponse {
    private LocalDate date;
    private Long count;
}
