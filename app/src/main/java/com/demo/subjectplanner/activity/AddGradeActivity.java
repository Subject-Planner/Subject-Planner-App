package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Dao;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.database.DatabaseSingleton;
import com.demo.subjectplanner.activity.database.SubjectDatabase;
import com.demo.subjectplanner.activity.model.Subject;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddGradeActivity extends AppCompatActivity {
    SubjectDatabase subjectDatabase;
    List<Subject> subjects ;
    String selectedSubject;
    String gradeWeight;
    String gradeTerm;
    Subject subject;

    //
    Spinner subjectSpinner;
    Date selectedDate;
    EditText gradeWeightEditText;
    EditText gradeTermEditText;
    ImageView saveGradeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);
        init();
        setUpSubjectsSpinner();
        collectGradeInfo();

    }
    private void init() {
        /*Room Database*/
        subjectDatabase = DatabaseSingleton.getInstance(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subjects= subjectDatabase.subjectDao().findAll();

    }
    private void setUpSubjectsSpinner(){
        Log.i("AddGradeActivity", "subjects from db: "+subjects.toString()+" titles: "+ subjects.stream().map(s->s.getTitle()).collect(Collectors.toList()));
        subjectSpinner = findViewById(R.id.subjects_category_spinner);
        subjectSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                subjects.stream().map(s->s.getTitle()).collect(Collectors.toList())));
    }
    private void collectGradeInfo(){
        // collect the associated date
        Button pickGradeDate= findViewById(R.id.grade_date_button);
        pickGradeDate.setOnClickListener(view -> {
            openDialog();
        });



        //save the grade
        saveGradeButton= findViewById(R.id.save_grade_imageview);
        saveGradeButton.setOnClickListener(view -> {
            //collect the associated subject
            selectedSubject = subjectSpinner.getSelectedItem().toString();
            // here you should find the subject based on its name after getting a list of them using amplify and completableFuture
            subject=subjectDatabase.subjectDao().findByTitle(selectedSubject);

            //collect the weight
            gradeWeightEditText= findViewById(R.id.grade_weight_addgrade);
            String gradeWeight = !gradeWeightEditText.getText().toString().isEmpty() ? gradeWeightEditText.getText().toString().trim() : "0";

            //collect the term
            gradeTermEditText= findViewById(R.id.term_grade_addgrade);
            String gradeTerm = !gradeTermEditText.getText().toString().isEmpty()  ? gradeTermEditText.getText().toString().trim() : "";
            Log.i("AddGradeActivity", "Subject: "+selectedSubject+" selected date: "+selectedDate+" selected weight:  "+gradeWeight+" selected term :  "+gradeTerm);

            subjectDatabase.subjectDao().updateGradeByTitle(Integer.valueOf(gradeWeight),selectedSubject);
            Snackbar.make(findViewById(R.id.addGradeActivityLayout), "Grade added", Snackbar.LENGTH_SHORT).show();
        });

    }
    private void openDialog() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year, month, day);
            // Convert the Calendar instance to a Date object
            selectedDate = calendar1.getTime();
        }, currentYear, currentMonth, currentDay);
        dialog.show();
    }
}