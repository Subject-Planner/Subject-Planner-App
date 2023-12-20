package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;


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

public class AgendaActivity extends AppCompatActivity implements EventRecyclerViewAdapter.OnEventDeletedListener {

    List<Event> events;
    EventRecyclerViewAdapter adapter;
    Student loggedInStudent;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        setupEventRecyclerView();
        getLoggedUser();
        goToMain();
    }

    public void setupEventRecyclerView() {
        RecyclerView eventRecyclerView = findViewById(R.id.recyclerViewEvents);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        eventRecyclerView.setLayoutManager(layoutManager);

        events = new ArrayList<>();

        adapter = new EventRecyclerViewAdapter(events, this, this);
        eventRecyclerView.setAdapter(adapter);
    }

    private void getEvents(){
        Log.i("Agenda Activity", "get events method called, user:" +loggedInStudent);

        if(loggedInStudent != null){
            List<Subject> loggedUserSubjects = loggedInStudent.getSubjects();
            List<Event> events2 = new ArrayList<>();

            for(Subject sub : loggedUserSubjects){
                for(Event event : sub.getEvents()) {
                    events2.add(event);
                }
            }
            events.clear();
            events.addAll(events2);

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

                        runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    } else {
                        Log.e("Agenda Activity", "User Not Found");
                    }
                },
                error -> {
                    Log.e("Agenda Activity", "Error fetching User by ID", error);
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEventDeleted() {
        getEvents();
        adapter.notifyDataSetChanged();

    }

    private void goToMain() {
        ImageView editSubjectButton=findViewById(R.id.agendaToHome);
        editSubjectButton.setOnClickListener(view -> {

            Intent goToEditSubjectIntent = new Intent(AgendaActivity.this,MainActivity.class );

            startActivity(goToEditSubjectIntent);
        });
    }
}