package com.demo.subjectplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.File;
import com.amplifyframework.datastore.generated.model.Record;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.EditRecordsRecyclerViewAdapter;
import com.demo.subjectplanner.activity.adapter.FileAdapter;
import com.demo.subjectplanner.activity.adapter.GradeAdapter;
import com.demo.subjectplanner.activity.adapter.RecordsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditSubjectActivity extends AppCompatActivity {
public static final String TAG="EditSubjectActivity";
    private TextView recordNameTextView;

    private RecyclerView recordsrecyclerView;
    private RecyclerView filessrecyclerView;
    private RecyclerView GradessrecyclerView;

    private String subjectIDFromIntent;
    private Subject subjectToEdit;
    private TextView  eventsTextView;
    private TextView  notesTextView;
    private TextView  daysTextView;
    private TextView  numberOfAbsentsTextView;
    private TextView  subjectNameTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        retrieveSubject();
    }
    private void retrieveSubject() {
        Intent callingIntent = getIntent();
        subjectIDFromIntent = callingIntent.getStringExtra(SubjectDetailsActivity.SUBJECT_TITLE);
        //   Log.i(SUBJECT_DETAILS, "retrieveSubject: " + subjectIDFromIntent);

        Amplify.API.query(
                ModelQuery.get(Subject.class, subjectIDFromIntent),
                response -> {
                     subjectToEdit= response.getData();

                    Log.i(TAG ,"subjectToEditafter : response.getData();" + subjectToEdit);
                    if (subjectToEdit!= null) {
                        runOnUiThread(() -> {
                            retrieveAllSubjectInfo();
//                            EditGrade();
//                            goToEditSubject();
//                            addRecord();
//                            addNote();
//                            addFile();
//                            addEvent();
                        });
                    } else {
                        // subjectToEditnot found
                        // Handle the case where the subjectToEditwith the given ID is not found
                    }
                },
                error -> {
                    // Handle query error
                    Log.e("GetSubjectError", "Error fetching subjectToEditby ID", error);
                }
        );
    }




    void retrieveAllSubjectInfo() {
        List<File> fileList = generateFileList();

        filessrecyclerView = findViewById(R.id.editFileRecyclerView);
        filessrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        FileAdapter adapter = new FileAdapter(fileList, this);
        filessrecyclerView.setAdapter(adapter);


        List<Record> recordList = generateRecordList();

        RecyclerView recordsrecyclerView = findViewById(R.id.EditRecordsRecyclerView);
        recordsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        EditRecordsRecyclerViewAdapter recordsRecyclerViewAdapter = new EditRecordsRecyclerViewAdapter(recordList, this);
        recordsrecyclerView.setAdapter(recordsRecyclerViewAdapter);



        RecyclerView gradeRecyclerView = findViewById(R.id.editGradesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(EditSubjectActivity.this, LinearLayoutManager.HORIZONTAL, false);
        gradeRecyclerView.setLayoutManager(layoutManager);
        GradeAdapter gradeAdapter = new GradeAdapter(subjectToEdit.getGrades(), EditSubjectActivity.this);
        gradeRecyclerView.setAdapter(gradeAdapter);



         subjectNameTextView = findViewById(R.id.EditSubjectTitleTextView);
        subjectNameTextView.setText(subjectToEdit.getTitle());

         numberOfAbsentsTextView = findViewById(R.id.editNumberOfAbsents);
        numberOfAbsentsTextView.setText("Number of Absents : "+subjectToEdit.getNumberOfAbsents());



        eventsTextView = findViewById(R.id.editEvents);
        eventsTextView.setText(concatenateEventDays(subjectToEdit.getEvents()));

        notesTextView=findViewById(R.id.editNotes);
        notesTextView.setText(createNumberedList(subjectToEdit.getNotes()));


    }
    public static String createNumberedList(List<String> items) {
        if (items.isEmpty()) {
            return "There is no note.";
        }

        StringBuilder result = new StringBuilder();
        result.append("Numbered List:\n");

        for (int i = 0; i < items.size(); i++) {
            result.append(i + 1).append(". ").append(items.get(i)).append("\n");
        }

        return result.toString();
    }

    public static String concatenateEventDays(List<Event> events) {
        if (events.isEmpty()) {
            return "There is no events.";
        }
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            result.append(event.getName());
            if (i < events.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
    public static String concatenateDaysEnum(List<DaysEnum> days) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < days.size(); i++) {
            result.append(days.get(i).name());
            if (i < days.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }



    private List<File> generateFileList() {
        List<File> fileList = subjectToEdit.getFiles();

        return fileList;
    }

    private List<Record> generateRecordList() {
        List<Record> recordList =subjectToEdit.getRecords();

        return recordList;
    }

}



