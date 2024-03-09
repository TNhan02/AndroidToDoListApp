package com.example.assignment1;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment1.data.Event;
import com.example.assignment1.data.EventDatabase;
import com.example.assignment1.data.EventRepository;
import com.example.assignment1.data.EventViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddEventDialog.AddEventListener {
    private AddEventDialog addEventDialog;
    private CustomAdapter customAdapter;
    private EventDatabase eventDatabase;
    private Button addButton, closeButton;
    private RecyclerView eventRecyclerView;
    private EventViewModel eventViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        int orientation = config != null ? config.orientation : Configuration.ORIENTATION_UNDEFINED;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main_land);
        }

        eventRecyclerView = findViewById(R.id.recyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        customAdapter = new CustomAdapter(eventRecyclerView);
        eventRecyclerView.setAdapter(customAdapter);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                customAdapter.setEvents(events);
                customAdapter.startColorUpdate();
            }
        });

        EventRepository eventRepository = new EventRepository(getApplication());
        addEventDialog = new AddEventDialog(eventRepository);

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            addButton = findViewById(R.id.addButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addEventDialog.show(getSupportFragmentManager(), "add_event_dialog");
                }
            });
        } else {
            addButton = findViewById(R.id.button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addEventDialog.show(getSupportFragmentManager(), "add_event_dialog");
                }
            });
        }
    }

    // method in adapter to be used in MainActivity
    @Override
    public void onEventAdded(Event event) {
        customAdapter.addEvent(event);
    }
}
