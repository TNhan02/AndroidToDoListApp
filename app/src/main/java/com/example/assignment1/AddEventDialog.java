package com.example.assignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.assignment1.data.Event;
import com.example.assignment1.data.EventRepository;
import com.example.assignment1.data.EventViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEventDialog extends DialogFragment {
    private EditText description, timestamp;
    private Button saveButton;
    private EventViewModel eventViewModel;

    public AddEventDialog(EventViewModel eventViewModel) {
        this.eventViewModel = eventViewModel;
    }

    public interface AddEventListener {
        // method for adding event to database
        void onEventAdded(Event event);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_event_dialog, container, false);

        description = rootView.findViewById(R.id.editDescriptionEvent);
        timestamp = rootView.findViewById(R.id.editTimestampEvent);
        saveButton = rootView.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventTimestamp = timestamp.getText().toString().trim();
                String eventDescription = description.getText().toString().trim();

                if (!eventDescription.isEmpty() && !eventTimestamp.isEmpty()) {
                    LocalDateTime eventTime = LocalDateTime.parse(eventTimestamp, DateTimeFormatter.ofPattern("yyyy-M-d HH:mm"));

                    Event newEvent = new Event();
                    newEvent.name = eventDescription;
                    newEvent.timestamp = eventTime;

                    // add new event to database
                    eventViewModel.create(newEvent);

                    // notify MainActivity of new event
                    if (getActivity() instanceof AddEventListener) {
                        ((AddEventListener) getActivity()).onEventAdded(newEvent);
                    }

                    dismiss();
                } else {
                    Toast.makeText(requireContext(), "Please fill in the timestamp and description of event", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
