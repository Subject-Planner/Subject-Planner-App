package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;
import static com.demo.subjectplanner.activity.model.CalendarUtils.daysInWeekArray;
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
import android.widget.ListView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.CalendarAdapter;
import com.demo.subjectplanner.activity.adapter.EventAdapter;
import com.demo.subjectplanner.activity.model.CalendarUtils;
import com.demo.subjectplanner.activity.model.Event;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    SharedPreferences sharedPreferences;
    Student loggedInStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        getLoggedUser();
        initWidgets();
        setWeekView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)

    {
if(loggedInStudent!=null){

        startActivity(new Intent(this, EventEditActivity.class));}
else
    Snackbar.make(findViewById(R.id.edit_event_layout), "Login to add events", Snackbar.LENGTH_SHORT).show();

    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }
    private void getLoggedUser(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String loggedUserId= sharedPreferences.getString(ID_TAG,"");
        Amplify.API.query(
                ModelQuery.get(Student.class, loggedUserId),
                response -> {
                    loggedInStudent = response.getData();
                    if (loggedInStudent != null) {

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