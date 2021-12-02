package com.example.recipes.service.impl;

import com.example.recipes.model.Event;
import com.example.recipes.repository.EventRepository;
import com.example.recipes.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void saveEvent(Event event) {
        this.eventRepository.save(event);
    }

    @Override
    public void findEventById(long id) {
        this.eventRepository.findById(id);
    }

    @Override
    public void removeById(long id) {
        eventRepository.deleteById(id);
    }
}
