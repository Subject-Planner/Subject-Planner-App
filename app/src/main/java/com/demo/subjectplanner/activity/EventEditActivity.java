package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.model.CalendarUtils;
import com.demo.subjectplanner.activity.model.Event;

import java.time.LocalTime;
import java.util.List;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    Spinner supjectSpinner;
    String selectedSubject;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        setupSubjectSpinner();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        supjectSpinner=findViewById(R.id.subjectSpinner_calender);
    }
    private void setupSubjectSpinner() {
        // retrieve a list of subjects then send their titles to this spinner
        List<String> temp = List.of("english","coding","calculus");
        supjectSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
               temp ));
    }
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        // selectedSubject = subjectSpinner.getSelectedItem().toString();
        //update event to have subject field
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}