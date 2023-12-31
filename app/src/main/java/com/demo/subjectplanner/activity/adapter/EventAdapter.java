package com.demo.subjectplanner.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.model.CalendarUtils;
import com.demo.subjectplanner.activity.model.Event;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        }

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        // Check if the event and its data are not null before displaying
        if (event != null) {
            String eventTitle = event.getName() + " " + CalendarUtils.formattedTime(event.getTime());
            eventCellTV.setText(eventTitle);
        } else {
            eventCellTV.setText("Event is null");
        }

        return convertView;
    }
}
