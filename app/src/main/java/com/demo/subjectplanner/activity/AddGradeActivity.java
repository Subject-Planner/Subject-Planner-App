package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Grade;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AddGradeActivity extends AppCompatActivity {
    String selectedSubject;
    CompletableFuture<List<Subject>> subjectFuture = new CompletableFuture<>();
Student loggedInStudent;
    SharedPreferences sharedPreferences;
    Spinner subjectSpinner;
    Date selectedDate;
    EditText gradeWeightEditText;
    EditText gradeTermEditText;
    ImageView saveGradeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);
getLoggedUser();


    }


    private  void collectGradeInfo(){
        // collect the associated date
        ImageView pickGradeDate= findViewById(R.id.grade_date_button);
        pickGradeDate.setOnClickListener(view -> {
            openDialog();
        });
        Log.i("AddGradeActivity", "logged in user: "+loggedInStudent);

        if(loggedInStudent!=null){

        //save the grade
        saveGradeButton= findViewById(R.id.save_grade_imageview);
        saveGradeButton.setOnClickListener(view -> {
            //collect the associated subject
            selectedSubject = subjectSpinner.getSelectedItem().toString();
            List<Subject> subs = null;
            try {
                subs = subjectFuture.get();
            } catch (InterruptedException ie) {
                Log.e("Grade Activity", " InterruptedException while getting subjects");
            } catch (ExecutionException ee) {
                Log.e("Grade Activity", " ExecutionException while getting subjects");
            }
            Subject selectedSub = subs.stream().filter(c -> c.getTitle().equals(selectedSubject)).findAny().orElseThrow(RuntimeException::new);

            //collect the weight
            gradeWeightEditText= findViewById(R.id.grade_weight_addgrade);
            String gradeWeight = !gradeWeightEditText.getText().toString().isEmpty() ? gradeWeightEditText.getText().toString().trim() : "0";

            //collect the term
            gradeTermEditText= findViewById(R.id.term_grade_addgrade);
            String gradeTerm = !gradeTermEditText.getText().toString().isEmpty()  ? gradeTermEditText.getText().toString().trim() : "";
            Log.i("AddGradeActivity", "Subject: "+selectedSub.toString()+" selected date: "+selectedDate+" selected weight:  "+gradeWeight+" selected term :  "+gradeTerm);
            Log.i("AddGradeActivity", "subject grades: "+selectedSub.getGrades().toString());
            Grade newGrade = Grade.builder()
                    .date(new Temporal.DateTime(selectedDate,0))
                            .term(gradeTerm)
                                    .weight(Integer.valueOf(gradeWeight))
                                            .subjectObject(selectedSub)
                                                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(newGrade),
                    successResponse -> Log.i("Add Grade Activity", " Grade added successfully"),//success response
                    failureResponse -> Log.e("Add Grade Activity", "Grade failed with this response" + failureResponse)// in case we have a failed response
            );
            Snackbar.make(findViewById(R.id.addGradeActivityLayout), "Grade Added", Snackbar.LENGTH_SHORT).show();
        });}else{
            Snackbar.make(findViewById(R.id.addGradeActivityLayout), "Login to add grade", Snackbar.LENGTH_SHORT).show();

        }

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
    private void setupSubjectSpinner(){  //here I get the subject using future complete and pass them to the spinner
        subjectSpinner = findViewById(R.id.subjects_category_spinner);

        ArrayList<String> subjectNames = new ArrayList<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        for (Subject subject : loggedInStudent.getSubjects()) {
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
    }
    private void getLoggedUser(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        String loggedUserId= sharedPreferences.getString(ID_TAG,"");
        Amplify.API.query(
                ModelQuery.get(Student.class, loggedUserId),
                response -> {
                    loggedInStudent = response.getData();
                    if (loggedInStudent != null) {
                        runOnUiThread(() -> {
                            setupSubjectSpinner();
                            collectGradeInfo();
                        });

                    } else {
                        Log.e("AddGradeActivity", "User Not Found");
                    }
                },
                error -> {
                    Log.e("AddGradeActivity", "Error fetching User by ID", error);
                }
        );
    }
}