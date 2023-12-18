package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.EventRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AgendaActivity extends AppCompatActivity {

    List<Event> events;
    EventRecyclerViewAdapter adapter;
    Student loggedInStudent;
    SharedPreferences sharedPreferences;
    CompletableFuture<List<Event>> retrievedEvents = new CompletableFuture<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        setupEventRecyclerView();
        getLoggedUser();
    }

    public void setupEventRecyclerView() {
        RecyclerView eventRecyclerView = findViewById(R.id.recyclerViewEvents);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        eventRecyclerView.setLayoutManager(layoutManager);

        events = new ArrayList<>();

        adapter = new EventRecyclerViewAdapter( events,this);
        eventRecyclerView.setAdapter(adapter);
    }

    private void getEvents(){
        Log.i("Calendar Activity", "get events method called, user:" +loggedInStudent);

        if(loggedInStudent != null){
            List<Subject> loggedUserSubjects = loggedInStudent.getSubjects();
            List<Event> events = new ArrayList<>();

            for(Subject sub : loggedUserSubjects){
                for(Event event : sub.getEvents()) {
                    events.add(event);
                }
            }

            retrievedEvents.complete(events);
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });

            // Update the adapter's dataset with retrievedEvents and notify the adapter of changes
            retrievedEvents.thenAccept(retrievedEventList -> {
                this.events.clear(); // Clear the existing events
                this.events.addAll(retrievedEventList); // Add the retrieved events
                adapter.notifyDataSetChanged(); // Notify adapter of dataset changes
            });
        }
    }

    private void getLoggedUser(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        String loggedUserId= sharedPreferences.getString(ID_TAG,"");
        Amplify.API.query(
                ModelQuery.get(Student.class, loggedUserId),
                response -> {
                    loggedInStudent = response.getData();
                    if (loggedInStudent != null) {
                        getEvents();
                    } else {
                        Log.e("EditEventActivity", "User Not Found");
                    }
                },
                error -> {
                    Log.e("EditEventActivity", "Error fetching User by ID", error);
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }
}