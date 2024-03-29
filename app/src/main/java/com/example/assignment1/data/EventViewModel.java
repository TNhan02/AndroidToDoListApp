package com.example.assignment1.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;
    private LiveData<List<Event>> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvents = eventRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
    public void create(Event event) {
        eventRepository.insert(event);
    }
    public void update(Event event) {
        eventRepository.update(event);
    }
    public void delete(Event event) {
        eventRepository.delete(event);
    }
}
