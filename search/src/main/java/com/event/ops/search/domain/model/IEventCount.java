package com.event.ops.search.domain.model;

import java.time.LocalDate;

public interface IEventCount {
    LocalDate getDate();
    Long getTotal();
}
