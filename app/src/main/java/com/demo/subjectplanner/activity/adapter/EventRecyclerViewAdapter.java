package com.demo.subjectplanner.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Event;
import com.demo.subjectplanner.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.EventViewHolder> {
    List<Event> events;
    Context callingActivity;

    public EventRecyclerViewAdapter(List<Event> events , Context callingActivity,OnEventDeletedListener listener) {
        this.events = events;
        this.callingActivity = callingActivity;
        this.onEventDeletedListener=listener;
        sortAndFilterEvents();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView= LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_cards, parent , false);
        return new EventRecyclerViewAdapter.EventViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        TextView eventTitleTextView = holder.itemView.findViewById(R.id.eventTitle);
        TextView eventDateTextView = holder.itemView.findViewById(R.id.eventViewDate);

        Event event = events.get(position);
        eventTitleTextView.setText(event.getName());
        String dateTime ="Time:" + event.getTime().toString().substring(45, 50);
        eventDateTextView.setText(dateTime);
        Button deleteEventButton = holder.itemView.findViewById(R.id.deleteEvent);
        deleteEventButton.setOnClickListener(view -> {
            deleteThisEvent(event);
        });
    }

    private void deleteThisEvent(Event event) {
        Amplify.API.mutate(
                ModelMutation.delete(event),
                response -> {
                    onEventDeletedListener.onEventDeleted();

                    System.out.println("event deleted successfully");
                },
                error -> {
                    System.err.println("Error deleting event: " + error);
                }
        );
    }

    private void sortAndFilterEvents() {
            // Sort events based on date and time
            Collections.sort(events, (event1, event2) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                try {
                    Date dateTime1 = sdf.parse(event1.getDate() + " " + event1.getTime());
                    Date dateTime2 = sdf.parse(event2.getDate() + " " + event2.getTime());
                    return dateTime1.compareTo(dateTime2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            });

            // Remove events that have passed
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Date currentDateTime = new Date();

            for (int i = events.size() - 1; i >= 0; i--) {
                try {
                    Date eventDateTime = sdf.parse(events.get(i).getDate() + " " + events.get(i).getTime());
                    if (currentDateTime.after(eventDateTime)) {
                        events.remove(i);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            notifyDataSetChanged();
        }


    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public interface OnEventDeletedListener {
        void onEventDeleted();
    }
    private OnEventDeletedListener onEventDeletedListener;

}
