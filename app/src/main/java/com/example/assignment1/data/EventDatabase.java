package com.example.assignment1.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = Event.class, version = 2)
@TypeConverters({Converters.class})
public abstract class EventDatabase extends RoomDatabase {
    private static EventDatabase instance;
    public abstract EventDao eventDao();

    public static synchronized EventDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EventDatabase.class, "event_database")
                    .build();
        }
        return instance;
    }

    public static final Executor databaseWriteExecutor = Executors.newSingleThreadExecutor();
    public static final Executor databaseReadExecutor = Executors.newFixedThreadPool(3);
    public static Executor getDatabaseReadExecutor() {
        return databaseReadExecutor;
    }
}
