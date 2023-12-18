package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;
import static com.demo.subjectplanner.activity.model.CalendarUtils.daysInMonthArray;
import static com.demo.subjectplanner.activity.model.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.CalendarAdapter;
import com.demo.subjectplanner.activity.model.CalendarUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Calendar extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    //
    CompletableFuture<List<Event>> retrievedEvents = new CompletableFuture<>();
    Student loggedInStudent;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getLoggedUser();

        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate)==null?"":monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
          setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

    private void setupSubjectActivities(){
        Log.i("Calendar Activity", "setupSubjectActivities method called, user:" +loggedInStudent);
        List<Event> eve =new ArrayList<>();
        com.demo.subjectplanner.activity.model.Event.eventsList.clear();

        if (loggedInStudent!=null){

        try {
            eve = retrievedEvents.get();
        } catch (InterruptedException ie) {
            Log.e("Calendar Activity", " InterruptedException while getting events");
        } catch (ExecutionException ee) {
            Log.e("Calendar Activity", " ExecutionException while getting events");
        }
            Log.i("Calendar Activity", "setupSubjectActivities method called, events:" +eve.toString());
            for(Event event:eve){
                Log.i("Calendar Activity", "setupSubjectActivities method called, inside loop:" +eve.toString());

                LocalTime time3 = LocalTime.of(Integer.valueOf(event.getTime().toString().substring(45,47)),Integer.valueOf(event.getTime().toString().substring(48,50)));
            Log.i("Calender Activity", " retrieved events: "+event.getName()+ " time mins "+event.getTime().toString().substring(48,50)+"  hrs "+event.getTime().toString().substring(45,47)+" date "+event.getTime().toString());
            Temporal.DateTime dateTime = event.getTime();

// Convert DateTime to Instant
            Instant instant = dateTime.toDate().toInstant();

// Convert Instant to LocalDate
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            Log.i("Calender Activity","parsed date: "+localDate.toString());
            com.demo.subjectplanner.activity.model.Event newEvent = new com.demo.subjectplanner.activity.model.Event(event.getName(), localDate, time3);
            com.demo.subjectplanner.activity.model.Event.eventsList.add(newEvent);
        }}

    }
    private void getEvents(){
        Log.i("Calendar Activity", "get events method called, user:" +loggedInStudent);

        if(loggedInStudent!=null){
        List<Subject>loggedUserSubjects= loggedInStudent.getSubjects();
        ArrayList<String> eventNames = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        for(Subject sub:loggedUserSubjects){
        for (Event event : sub.getEvents()) {
            events.add(event);
            eventNames.add(event.getName());
        }}
        retrievedEvents.complete(events);
    }}

    private void getLoggedUser(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        String loggedUserId= sharedPreferences.getString(ID_TAG,"");
        Amplify.API.query(
                ModelQuery.get(Student.class, loggedUserId),
                response -> {
                    loggedInStudent = response.getData();
                    if (loggedInStudent != null) {
                        getEvents();
                        setupSubjectActivities();
                    } else {
                        Log.e("EditEventActivity", "User Not Found");
                    }
                },
                error -> {
                    Log.e("EditEventActivity", "Error fetching User by ID", error);
                }
        );
    }
}