package com.example.assignment1.data;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "event")
public class Event {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "timestamp")
    public LocalDateTime timestamp;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "backgroundColor")
    public int backgroundColor;
}
