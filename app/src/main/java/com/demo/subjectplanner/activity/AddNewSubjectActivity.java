package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
//import com.demo.subjectplanner.activity.database.DatabaseSingleton;
//import com.demo.subjectplanner.activity.database.DayConverter;
//import com.demo.subjectplanner.activity.database.SubjectDatabase;
import com.demo.subjectplanner.activity.model.CalendarUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewSubjectActivity extends AppCompatActivity {
    //SubjectDatabase subjectDatabase;
    ImageView pickStartTimeButton;
    TextView startTimeView;
    MultiAutoCompleteTextView daysMultiAutoCompleteTextView;
    //
    Date subjectStartDate;
    String subjectTitle;
    String subjectAbsents;
    SharedPreferences sharedPreferences;
    Student loggedInStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subject);
        init();

        setDaysPicker();
        getLoggedUser();
        new Handler().postDelayed(() -> animationLinearLayout(), 500); // Adjust the delay as needed
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Agenda) {
            Toast.makeText(this, "go to Agenda", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.Recordings) {
            Toast.makeText(this, "go to Recordings", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.Calendar) {
            Toast.makeText(this, "go to Calendar", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.Grades) {
            Toast.makeText(this, "go to Grades", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void init() {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        startTimePicker();
    }

    private void startTimePicker() {
        startTimeView = findViewById(R.id.start_time_textview);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        // default time
        startTimeView.setText("Time: " + sdf.format(Calendar.getInstance().getTime()));
        // default time if the time is not opened
        subjectStartDate = Calendar.getInstance().getTime();
        pickStartTimeButton = findViewById(R.id.startTimeButton);
        pickStartTimeButton.setOnClickListener(view -> {
            startTimeDialog();
        });
    }

    private void startTimeDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                startTimeView.setText("Time: " + hours + " : " + minutes);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hours); // Replace 'hours' with the selected hours
                calendar.set(Calendar.MINUTE, minutes);
                subjectStartDate = calendar.getTime();
            }
        }, 12, 00, false);
        timePickerDialog.show();
    }

    private void saveSubjectToDB() {
            EditText title = findViewById(R.id.subjectTitleText);
            EditText Absents = findViewById(R.id.nuberOfAbsents_editText);

            Button saveSubjectToDb = findViewById(R.id.save_subject_todb_button);
            saveSubjectToDb.setOnClickListener(view -> {
            subjectTitle = title.getText().toString().trim().isEmpty() ? "Temp" : title.getText().toString().trim();
            subjectAbsents = !Absents.getText().toString().isEmpty() ? Absents.getText().toString() : "0";
            String selectedDays = daysMultiAutoCompleteTextView.getText().toString()!=null?daysMultiAutoCompleteTextView.getText().toString():" FRIDAY, SATURDAY ";

            List<DaysEnum> selectedDayNames = DaysEnum.fromStringList( Arrays.asList(selectedDays.trim().split(",")));
//convert time to UTC
                // Convert java.util.Date to Instant
                Instant instant = subjectStartDate.toInstant();

                // Convert Instant to LocalDateTime in UTC
                LocalDateTime utcLocalDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

                // Convert LocalDateTime to Instant
               // Instant utcInstant = utcLocalDateTime.toInstant(ZoneOffset.UTC);

                // Convert Instant back to java.util.Date
                Date utcDate = Date.from(instant);
                Log.i("AddNewSubjectActivity", "std: "+ loggedInStudent.toString()+"  sub: " + subjectTitle + " subject absents: " + subjectAbsents + " subject date: " + utcDate.toString()+ " selected days : " + selectedDays+" enums: "+selectedDayNames);

                Subject newSubj = Subject.builder()
                        .title(subjectTitle)
                        .images(new ArrayList<>())
                        .files(new ArrayList<>())
                        .notes(new ArrayList<>())
                        .recordings(new ArrayList<>())
                        .startDate(new Temporal.DateTime(utcDate, 0))
                        .endDate(new Temporal.DateTime(new Date(), 0))
                        .numberOfAbsents(Integer.valueOf(subjectAbsents))
                        .days(selectedDayNames)
                        .studentPerson(loggedInStudent)
                        .build();


                Amplify.API.mutate(
                        ModelMutation.create(newSubj),
                        successResponse ->{
                            Log.i("AddNewSubjectActivity", "AddSubject.onCreate(): Subject added successfully");
                            Snackbar.make(findViewById(R.id.AddNewSubjectLayout), "Subject Added", Snackbar.LENGTH_SHORT).show();
                        },
                        failureResponse -> Log.e("AddNewSubjectActivity", "AddSubject.onCreate(): failed with this response" + failureResponse)// in case we have a failed response
                );
       //     addSubject(subjectTitle,subjectStartDate,Integer.valueOf(subjectAbsents),selectedDayNames);


        });
    }

    private void setDaysPicker() {
        daysMultiAutoCompleteTextView = findViewById(R.id.daysMultiAutoCompleteTextView);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, DaysEnum.toStringList(List.of( DaysEnum.values())));

// Set the adapter to the MultiAutoCompleteTextView
        daysMultiAutoCompleteTextView.setAdapter(adapter);

// Set a tokenizer to tokenize the input based on a comma
        daysMultiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

//    private void addSubject(String title, Date subjectStartDate, int absents ,List<DaysEnum> daysEnums) {
//
////        Student student = Student.builder()
////                .name("saif")
////                .email("saif@yahoo.com")
////                .password("saif123").build();
//        Subject newSubj = Subject.builder()
//                .title(title)
//                .images(new ArrayList<>())
//                .files(new ArrayList<>())
//                .notes(new ArrayList<>())
//                .recordings(new ArrayList<>())
//                .startDate(new Temporal.DateTime(subjectStartDate, 0))
//                .endDate(new Temporal.DateTime(new Date(), 0))
//                .numberOfAbsents(absents)
//                .days(daysEnums)
//                .studentPerson(loggedInStudent)
//                .build();
//
//
//        Amplify.API.mutate(
//                ModelMutation.create(newSubj),
//                successResponse -> Log.i("AddNewSubjectActivity", "AddSubject.onCreate(): Subject added successfully"),//success response
//                failureResponse -> Log.e("AddNewSubjectActivity", "AddSubject.onCreate(): failed with this response" + failureResponse)// in case we have a failed response
//        );
//    }
    private void getLoggedUser(){

        String loggedUserId= sharedPreferences.getString(ID_TAG,"");
        Amplify.API.query(
                ModelQuery.get(Student.class, loggedUserId),
                response -> {
                    loggedInStudent = response.getData();
                    if (loggedInStudent != null) {

                  saveSubjectToDB();

                    } else {
                        Log.e("AddNewSubjectActivity", "User Not Found");
                    }
                },
                error -> {
                    Log.e("AddNewSubjectActivity", "Error fetching User by ID", error);
                }
        );
    }
    private void animationLinearLayout(){
        int animationDuration = 1000;

        // Get references to your LinearLayouts
        LinearLayout titleLayout = findViewById(R.id.titleLayout);
        LinearLayout timeLayout = findViewById(R.id.timeLayout);
        LinearLayout absentsLayout = findViewById(R.id.absentsLayout);
        LinearLayout daysLayout = findViewById(R.id.daysLayout);

         //Slide animations
        titleLayout.startAnimation(getSlideAnimation(0, 0, -titleLayout.getHeight(), 0, animationDuration));
        timeLayout.startAnimation(getSlideAnimation(0, 0, -timeLayout.getHeight(), 0, animationDuration));
        absentsLayout.startAnimation(getSlideAnimation(0, 0, -absentsLayout.getHeight(), 0, animationDuration));
        daysLayout.startAnimation(getSlideAnimation(0, 0, -daysLayout.getHeight(), 0, animationDuration));
//
//        rotateAnimation(titleLayout,1000);
//     rotateAnimation(daysLayout,animationDuration);
//        rotateAnimation(absentsLayout,animationDuration);
//        rotateAnimation(timeLayout,animationDuration);

//        bounceAnimation(timeLayout,animationDuration);
//        bounceAnimation(titleLayout,animationDuration);
//        bounceAnimation(daysLayout,animationDuration);
//        bounceAnimation(absentsLayout,animationDuration);


        scaleAnimation(timeLayout,animationDuration);
        scaleAnimation(titleLayout,animationDuration);
        scaleAnimation(daysLayout,animationDuration);
        scaleAnimation(absentsLayout,animationDuration);


    }
    private Animation getSlideAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, int duration) {
        Animation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(duration);
        return animation;
    }
    private void fadeInAnimation(View view, int duration) {
        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void scaleAnimation(View view, int duration) {
        Animation animation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
    private void bounceAnimation(View view, int duration) {
        Animation animation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(1);
        view.startAnimation(animation);
    }
    private void rotateAnimation(View view, int duration) {
        Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

}