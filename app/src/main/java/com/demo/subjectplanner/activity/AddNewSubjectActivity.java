package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
//import com.demo.subjectplanner.activity.database.DatabaseSingleton;
//import com.demo.subjectplanner.activity.database.DayConverter;
//import com.demo.subjectplanner.activity.database.SubjectDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewSubjectActivity extends AppCompatActivity {
//SubjectDatabase subjectDatabase;
Button pickStartTimeButton;
TextView startTimeView;
    MultiAutoCompleteTextView daysMultiAutoCompleteTextView;
//
    Date subjectStartDate;
    String subjectTitle;
    String subjectAbsents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subject);
        init();
        setDaysPicker();
        saveSubjectToDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.Agenda){
            Toast.makeText(this,"go to Agenda",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Recordings){
            Toast.makeText(this,"go to Recordings",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Calendar){
            Toast.makeText(this,"go to Calendar",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Grades){
            Toast.makeText(this,"go to Grades",Toast.LENGTH_LONG).show();
        }
        return true;
    }
    private void init() {
        /*Room Database*/
        //subjectDatabase = DatabaseSingleton.getInstance(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
startTimePicker();
    }
    private void startTimePicker(){
        startTimeView = findViewById(R.id.start_time_textview);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        // default time
        startTimeView.setText("Time: "+sdf.format(Calendar.getInstance().getTime()));
        // default time if the time is not opened
        subjectStartDate=Calendar.getInstance().getTime();
        pickStartTimeButton= findViewById(R.id.startTimeButton);
        pickStartTimeButton.setOnClickListener(view -> {
startTimeDialog();
        });
    }
   private void startTimeDialog(){
       TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
               startTimeView.setText("Time: "+ hours+" : "+minutes);
               Calendar calendar = Calendar.getInstance();
               calendar.set(Calendar.HOUR_OF_DAY, hours); // Replace 'hours' with the selected hours
               calendar.set(Calendar.MINUTE, minutes);
                subjectStartDate = calendar.getTime();
           }
       }, 12, 00, false);
       timePickerDialog.show();
   }
   private void saveSubjectToDB(){
        EditText title = findViewById(R.id.subjectTitleText);
       EditText Absents= findViewById(R.id.nuberOfAbsents_editText);

        Button saveSubjectToDb= findViewById(R.id.save_subject_todb_button);
        saveSubjectToDb.setOnClickListener(view -> {
            subjectTitle=title.getText().toString().trim().isEmpty()?"Temp":title.getText().toString().trim();
            subjectAbsents=!Absents.getText().toString().isEmpty()?Absents.getText().toString():"0";
            String selectedDays = daysMultiAutoCompleteTextView.getText().toString();
            List<String> selectedDayNames = Arrays.asList(selectedDays.split(","));

            // Convert selected day names to DayOfWeek enum values
//            List<DayOfWeek> selectedDaysList = DayConverter.convertToDayOfWeekList(selectedDayNames).isEmpty()?DayConverter.convertToDayOfWeekList(List.of("friday")):DayConverter.convertToDayOfWeekList(selectedDayNames);
            Log.i("AddNewSubjectTag", "subject: "+subjectTitle+" subject absents: "+subjectAbsents+" subject date: " +subjectStartDate.toString()+" selected days : "+selectedDays);

//            Subject newSubject = new Subject(subjectTitle,"","","","noNotes",subjectStartDate,new Date(),new Date(),new Date(),0,Integer.valueOf(subjectAbsents));
//            Subject newSubject = new Subject();
//            newSubject.setTitle(subjectTitle);
//            newSubject.setStartTime(subjectStartDate);
//            newSubject.setGrades(0);
//            newSubject.setNumberOfAbsents(Integer.valueOf(subjectAbsents));
//            newSubject.setDaysOfWeek(selectedDaysList);
           // subjectDatabase.subjectDao().insertSubject(newSubject);
            Snackbar.make(findViewById(R.id.AddNewSubjectLayout), "Subject Added", Snackbar.LENGTH_SHORT).show();

        });

   }
    private void setDaysPicker(){
         daysMultiAutoCompleteTextView = findViewById(R.id.daysMultiAutoCompleteTextView);

// Create an array of days
        String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, daysArray);

// Set the adapter to the MultiAutoCompleteTextView
        daysMultiAutoCompleteTextView.setAdapter(adapter);

// Set a tokenizer to tokenize the input based on a comma
        daysMultiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }
}