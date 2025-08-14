package com.event.ops.search.application.service;

import com.event.ops.auth.application.service.CurrentClientService;
import com.event.ops.search.application.ports.SearchService;
import com.event.ops.search.domain.model.IEventCount;
import com.event.ops.search.infrastructure.persistence.EventRepository;
import com.event.ops.search.infrastructure.web.dto.DailyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final EventRepository eventRepository;
    private final CurrentClientService currentClientService;

    @Autowired
    public SearchServiceImpl(EventRepository eventRepository, CurrentClientService currentClientService) {
        this.eventRepository = eventRepository;
        this.currentClientService = currentClientService;
    }

    @Override
    public Long eventCount(String eventName) {
        if (eventName != null)
            return eventRepository.countByClientIdAndEventName(currentClientService.getCurrentClient().getId(), eventName);
        else
            return eventRepository.countByClient_Id(currentClientService.getCurrentClient().getId());
    }

    @Override
    @Cacheable(value = "dailyAggregate", key = "#p0")
    public List<DailyResponse> dailyAggregate(String eventName) {
        List<IEventCount> list = eventRepository.aggregateByDateAndEventName(eventName);

        return list.stream()
                .map(i -> new DailyResponse(i.getDate().toString(), i.getTotal()))
                .toList();
    }
}
