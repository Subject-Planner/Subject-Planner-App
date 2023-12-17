package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;
import static com.demo.subjectplanner.activity.model.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

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
import com.demo.subjectplanner.activity.adapter.HourAdapter;
import com.demo.subjectplanner.activity.model.CalendarUtils;
import com.demo.subjectplanner.activity.model.Event;
import com.demo.subjectplanner.activity.model.HourEvent;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity {

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    SharedPreferences sharedPreferences;
    Student loggedInStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        getLoggedUser();
        initWidgets();
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
       // dayOfWeekTV.setTextColor(getResources().getColor(R.color.primary)); // Set your desired blue color here
        setHourAdapter();
    }

    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++)
        {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }

    public void previousDayAction(View view)
    {
        selectedDate = selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        selectedDate = selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view)

    {
        if(loggedInStudent!=null){

            startActivity(new Intent(this, EventEditActivity.class));}
        else
            Snackbar.make(findViewById(R.id.daily_layout), "Login to add events", Snackbar.LENGTH_SHORT).show();

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