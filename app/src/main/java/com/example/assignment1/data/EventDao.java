package com.example.assignment1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void insertEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);
}
