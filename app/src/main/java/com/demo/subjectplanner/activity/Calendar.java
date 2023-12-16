package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.model.CalendarUtils.daysInMonthArray;
import static com.demo.subjectplanner.activity.model.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Event;
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



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        getEvents();
        setupSubjectActivities();
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
        List<Event> eve = null;
        try {
            eve = retrievedEvents.get();
        } catch (InterruptedException ie) {
            Log.e("Event Activity", " InterruptedException while getting events");
        } catch (ExecutionException ee) {
            Log.e("Event Activity", " ExecutionException while getting events");
        }
        for(Event event:eve){
//            Instant instant = event.getDate().toDate().toInstant();
//
//            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
//            LocalTime time1 = localDateTime.toLocalTime();
//            LocalTime time2 = instant.atZone(ZoneOffset.UTC).toLocalTime();
            LocalTime time3 = LocalTime.of(Integer.valueOf(event.getTime().toString().substring(45,47)),Integer.valueOf(event.getTime().toString().substring(48,50)));
            Log.i("Event Activity", " retrieved events: "+event.getName()+ " time mins "+event.getTime().toString().substring(48,50)+"  hrs "+event.getTime().toString().substring(45,47));

            com.demo.subjectplanner.activity.model.Event newEvent = new com.demo.subjectplanner.activity.model.Event(event.getName(), CalendarUtils.selectedDate, time3);
            com.demo.subjectplanner.activity.model.Event.eventsList.add(newEvent);
        }
        // use intent to resieve it here with loged in user and loop through his subjs and events to do cron job
//        com.demo.subjectplanner.activity.model.Event newEvent = new com.demo.subjectplanner.activity.model.Event(eventName, CalendarUtils.selectedDate, time);
//        com.demo.subjectplanner.activity.model.Event.eventsList.add(newEvent);
    }
    private void getEvents(){
        Amplify.API.query(
                ModelQuery.list(Event.class),
                success ->
                {
                    Log.i("Calendar activity", "Read events Successfully");
                    ArrayList<String> eventNames = new ArrayList<>();
                    ArrayList<Event> events = new ArrayList<>();
                    for (Event event : success.getData()) {
                        events.add(event);
                        eventNames.add(event.getName());
                    }
retrievedEvents.complete(events);

                },
                failure -> {

                    Log.i("Calendar Activity", "Did not read events successfully");
                }
        );
    }
}