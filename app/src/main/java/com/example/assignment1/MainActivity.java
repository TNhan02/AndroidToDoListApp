package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment1.data.Event;
import com.example.assignment1.data.EventDatabase;
import com.example.assignment1.data.EventRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddEventDialog.AddEventListener {
    private AddEventDialog addEventDialog;
    private CustomAdapter customAdapter;
    private EventDatabase eventDatabase;
    private Button addButton, closeButton;
    private RecyclerView eventRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            eventRecyclerView = findViewById(R.id.recyclerView);
            eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            customAdapter = new CustomAdapter(eventRecyclerView);
            eventRecyclerView.setAdapter(customAdapter);

            eventDatabase = EventDatabase.getInstance(this);
            eventDatabase.eventDao().getAllEvents().observe(this, new Observer<List<Event>>() {
                @Override
                public void onChanged(List<Event> events) {
                    customAdapter.setEvents(events);
                }
            });

            EventRepository eventRepository = new EventRepository(getApplication());
            addEventDialog = new AddEventDialog(eventRepository);

            addButton = findViewById(R.id.addButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addEventDialog.show(getSupportFragmentManager(), "add_event_dialog");
                }
            });

            // start color update when activity starts
            customAdapter.startColorUpdate();
        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //check if the new configuration is landscape
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main_land);
        }
        else {
            setContentView(R.layout.activity_main);
        }

        eventRecyclerView = findViewById(R.id.recyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(eventRecyclerView);
        eventRecyclerView.setAdapter(customAdapter);
    }

    // method in adapter to be used in MainActivity
    @Override
    public void onEventAdded(Event event) {
        customAdapter.addEvent(event);
    }
}
