package com.event.ops.search.application.service;

import com.event.ops.search.application.ports.SearchService;
import com.event.ops.search.domain.model.IEventCount;
import com.event.ops.search.infrastructure.persistence.EventRepository;
import com.event.ops.search.infrastructure.web.dto.DailyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final EventRepository eventRepository;

    @Autowired
    public SearchServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Long eventCount(String eventName) {
        if (eventName != null)
            return eventRepository.countByEventName(eventName);
        else
            return eventRepository.count();
    }

    @Override
    public List<DailyResponse> dailyAggregate(String eventName) {
        List<IEventCount> list = eventRepository.aggregateByDateAndEventName(eventName);

        return list.stream()
                .map(ievent -> new DailyResponse(ievent.getDate(), ievent.getTotal()))
                .toList();
    }
}
