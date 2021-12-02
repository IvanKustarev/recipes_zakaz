package com.example.recipes.service;

import com.example.recipes.model.Event;

public interface EventService {
    void saveEvent(Event event);
    void findEventById(long id);
    void removeById(long id);
}
