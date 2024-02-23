package com.example.assignment1.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        EventDatabase database = EventDatabase.getInstance(application);
        eventDao = database.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    // CRUD operations for database operations
    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
    public void insert(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insertEvent(event);
        });
    }
    public void update(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.updateEvent(event);
        });
    }
    public void delete(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteEvent(event);
        });
    }
}
