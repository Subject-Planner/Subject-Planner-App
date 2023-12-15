package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
//import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.model.CalendarUtils;
//import com.demo.subjectplanner.activity.model.Event;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    Spinner subjectSpinner;
    String selectedSubjectString;
    ImageView timeButton ;
    private LocalTime time;
    //
    CompletableFuture<List<Subject>> subjectFuture = new CompletableFuture<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        setupSubjectSpinner();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
       // eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
timeButton= findViewById(R.id.chooseTimeButton_event);
timeButton.setOnClickListener(v->{
    showTimePickerDialog();
});
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        subjectSpinner=findViewById(R.id.subjectSpinner_calender);
    }


public void saveEventAction(View view)
{
    String eventName = eventNameET.getText().toString();
    selectedSubjectString = subjectSpinner.getSelectedItem().toString();
    List<Subject> subs = null;
    try {
        subs = subjectFuture.get();
    } catch (InterruptedException ie) {
        Log.e("Event Activity", " InterruptedException while getting subjects");
    } catch (ExecutionException ee) {
        Log.e("Event Activity", " ExecutionException while getting subjects");
    }
// Assuming CalendarUtils.selectedDate is of type LocalDate
    LocalDate selectedDate = CalendarUtils.selectedDate;
// Use the Instant to create Temporal.DateTime
    Date selectedDateAsDate = Date.from(selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant());

    // Combine date and time
    LocalDateTime localDateTime = LocalDateTime.of(selectedDate, time);
// Convert LocalDateTime to Date
    Date timeAsDate = Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
    Subject selectedSub = subs.stream().filter(c -> c.getTitle().equals(selectedSubjectString)).findAny().orElseThrow(RuntimeException::new);
    Log.i("EventActivity", "saveEventAction: "+ " name "+eventName+ " date: "+CalendarUtils.selectedDate.toString()+" time: "+time.toString()+" subject: "+selectedSub.getEvents().toString());

    Event newOne =Event.builder()
            .name(eventName)
            .date(new Temporal.DateTime(selectedDateAsDate,0))
                    .time(new Temporal.DateTime(timeAsDate,0))
                                    .subject(selectedSub)
                                            .build();
//    Amplify.API.mutate(
//            ModelMutation.create(newOne),
//            successResponse -> Log.i("EventActivity", "SaveEvenTAction.onCreate(): Event added successfully"),//success response
//            failureResponse -> Log.e("EventActivity", "SaveEvenTAction.onCreate(): faile d with this response" + failureResponse)// in case we have a failed response
//    );
//here no need to add the events to the lis. in calender they are fitched from the current std subjs and bokked on calender
//    com.demo.subjectplanner.activity.model.Event newEvent = new com.demo.subjectplanner.activity.model.Event(eventName, CalendarUtils.selectedDate, time);
//    com.demo.subjectplanner.activity.model.Event.eventsList.add(newEvent);
    finish();
}

private void showTimePickerDialog(){
    TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
            eventTimeTV.setText("Time: " + hours + " : " + minutes);
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.HOUR_OF_DAY, hours); // Replace 'hours' with the selected hours
            calendar.set(Calendar.MINUTE, minutes);
            time = LocalTime.of(hours,minutes);
        }
    }, 12, 00, false);
    timePickerDialog.show();
    eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));

}
    private void setupSubjectSpinner(){  //here I get the subject using future complete and pass them to the spinner
        Amplify.API.query(
                ModelQuery.list(Subject.class),
                success ->
                {
                    Log.i("Event activity", "Read Subjects Successfully");
                    ArrayList<String> subjectNames = new ArrayList<>();
                    ArrayList<Subject> subjects = new ArrayList<>();
                    for (Subject subject : success.getData()) {
                        subjects.add(subject);
                        subjectNames.add(subject.getTitle());
                    }
                    subjectFuture.complete(subjects);

                    runOnUiThread(() ->
                    {
                        subjectSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                (android.R.layout.simple_spinner_item),
                                subjectNames
                        ));
                    });
                },
                failure -> {
                    subjectFuture.complete(null);
                    Log.i("Event Activity", "Did not read Subjects successfully");
                }
        );
    }
}