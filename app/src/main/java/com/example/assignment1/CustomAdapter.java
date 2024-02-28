package com.example.assignment1;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment1.data.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Event> allEvents = new ArrayList<>();
    private RecyclerView recyclerView;
    private final Handler handler = new Handler(Looper.getMainLooper());     // handler to update colors periodically
    private Timer timer; // timer to arrange the time of checking current datetime to update colors

    public CustomAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void startColorUpdate() {
        // update color every 1 minute
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateColors();
            }
        }, 0, 60000);
    }
    public void updateColors() {
        for(int i = 0; i < allEvents.size(); i++) {
            Event event = allEvents.get(i);
            LocalDateTime eventTimestamp = event.timestamp;

            // find the ViewHolder for the current position
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder instanceof ViewHolder) {
                ViewHolder holder = (ViewHolder) viewHolder;
                TextView eventTextView = holder.eventTextView;
                updateItemColor(eventTextView, eventTimestamp);
            }
        }
    }


    public void setEvents(List<Event> events) {
        allEvents.clear();
        allEvents.addAll(events);
        notifyDataSetChanged(); // notify RecyclerView of dataset change
    }

    public void addEvent(Event event) {
        allEvents.add(event);
        notifyItemInserted(allEvents.size() - 1); // notify RecyclerView of dataset change
    }


    private void updateItemColor(TextView textView, LocalDateTime eventTimestamp) {
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = eventTimestamp.toLocalDate();

        // default color
        int color = ContextCompat.getColor(textView.getContext(), R.color.white);

        if (eventDate.isAfter(currentDate)) {
            color = ContextCompat.getColor(textView.getContext(), R.color.colorYellow);
        } else if (eventDate.isEqual(currentDate)) {
            color = ContextCompat.getColor(textView.getContext(), R.color.colorGreen);
        } else {
            color = ContextCompat.getColor(textView.getContext(), R.color.colorRed);
        }

        textView.setBackgroundColor(color);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Event event = allEvents.get(position);
        holder.bind(event);
        updateItemColor(holder.eventTextView, event.timestamp);
    }

    @Override
    public int getItemCount() {
        return allEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTextView = itemView.findViewById(R.id.textView);
        }

        public void bind(Event event) {
            String eventText = event.timestamp + " " + event.name;
            eventTextView.setText(eventText);
        }
    }
}
