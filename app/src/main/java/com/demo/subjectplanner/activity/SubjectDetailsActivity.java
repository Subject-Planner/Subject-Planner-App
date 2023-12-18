package com.demo.subjectplanner.activity;
import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;
import static com.demo.subjectplanner.activity.MainActivity.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.File;
import com.amplifyframework.datastore.generated.model.Record;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.FileAdapter;
import com.demo.subjectplanner.activity.adapter.GradeAdapter;
import com.demo.subjectplanner.activity.adapter.RecordsRecyclerViewAdapter;
import com.demo.subjectplanner.activity.model.FileEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SubjectDetailsActivity extends AppCompatActivity {
    public static final String SUBJECT_TITLE = "subjectTitle";
    public static final String SUBJECT_DETAILS = "subjectDetailsActivity";
    TextView subjectNameTextView;
    TextView numberOfAbsentsTextView;
    TextView daysTextView;
    TextView eventsTextView;
    TextView notesTextView;
    RecyclerView recyclerView;
    List<Subject> subjects;
    Subject subject;
    String subjectIDFromIntent;
    Student loggedInStudent;

    SharedPreferences sharedPreferences;

    CompletableFuture<List<Record>> recordFuture = new CompletableFuture<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject_details);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        retrieveSubject();


    }

    private void retrieveSubject() {
        Intent callingIntent = getIntent();
        subjectIDFromIntent = callingIntent.getStringExtra(MainActivity.SUBJECT_ID_TAG);
     //   Log.i(SUBJECT_DETAILS, "retrieveSubject: " + subjectIDFromIntent);

        Amplify.API.query(
                ModelQuery.get(Subject.class, subjectIDFromIntent),
                response -> {
                    subject = response.getData();

                    Log.i(SUBJECT_DETAILS, "subject after : response.getData();" + subject);
                    if (subject != null) {
                        runOnUiThread(() -> {
                            retrieveAllSubjectInfo();
                            addGrade();
                            goToEditSubject();
                             addRecord();
                             addNote();
                             addFile();
                             addEvent();
                        });
                    } else {
                        // Subject not found
                        // Handle the case where the Subject with the given ID is not found
                    }
                },
                error -> {
                    // Handle query error
                    Log.e("GetSubjectError", "Error fetching Subject by ID", error);
                }
        );
    }




    void retrieveAllSubjectInfo() {
        List<File> fileList = generateFileList();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        FileAdapter adapter = new FileAdapter(fileList, this);
        recyclerView.setAdapter(adapter);
        List<Record> recordList = generateRecordList();

        RecyclerView recordsrecyclerView = findViewById(R.id.SubjectRecordsRecyclerView);

        recordsrecyclerView.setLayoutManager(new LinearLayoutManager(SubjectDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        RecordsRecyclerViewAdapter recordsRecyclerViewAdapter = new RecordsRecyclerViewAdapter(recordList, SubjectDetailsActivity.this);
        recordsrecyclerView.setAdapter(recordsRecyclerViewAdapter);



        RecyclerView gradeRecyclerView = findViewById(R.id.grecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SubjectDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        gradeRecyclerView.setLayoutManager(layoutManager);
        GradeAdapter gradeAdapter = new GradeAdapter(subject.getGrades(), SubjectDetailsActivity.this);
        gradeRecyclerView.setAdapter(gradeAdapter);



        subjectNameTextView = findViewById(R.id.detailsSubjectTitleText);
        subjectNameTextView.setText(subject.getTitle());

        numberOfAbsentsTextView = findViewById(R.id.numberOfAbsents);
        numberOfAbsentsTextView.setText("Number of Absents : "+subject.getNumberOfAbsents());

        daysTextView = findViewById(R.id.days);
        daysTextView.setText(concatenateDaysEnum(subject.getDays()));

        eventsTextView = findViewById(R.id.events);
        eventsTextView.setText(concatenateEventDays(subject.getEvents()));

        notesTextView=findViewById(R.id.notes);
        notesTextView.setText(createNumberedList(subject.getNotes()));


    }





    private void addEvent() {
        Button addEventButton=(Button) findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(view -> {

            Intent goToAddEventIntent = new Intent(SubjectDetailsActivity.this,EditSubjectActivity.class );
            goToAddEventIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectIDFromIntent);
            startActivity(goToAddEventIntent);
        });
    }

    private void addFile() {
        Button addFileButton = findViewById(R.id.addFileButton);
        addFileButton.setOnClickListener(view -> {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            // Inflate the popup_addfiles.xml
            View popupView = inflater.inflate(R.layout.popup_addfiles, null);

            // Create a new instance of PopupWindow
            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Set background drawable to allow dismissal when clicking outside the popup window
            popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

            // Set focusable true to enable touch events outside of the popup window
            popupWindow.setFocusable(true);

            // Find the "Cancel" button in the popup layout
            Button cancelButton = popupView.findViewById(R.id.cancelButtonFilePopup);

            // Set an OnClickListener for the "Cancel" button to dismiss the popup
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss(); // Dismiss the popup when the "Cancel" button is clicked
                }
            });

            // Find views from the inflated layout (popupView)
            TextView popAddFileTitleTextView = popupView.findViewById(R.id.addFilePopupTitle);
            EditText popFileName = popupView.findViewById(R.id.addFileNamePopupEditText);
            EditText popFilePath = popupView.findViewById(R.id.addFileLinkPopupEditText);

            // Set the text for popAddFileTitleTextView
            popAddFileTitleTextView.setText("Add Your File to : " + subject.getTitle());

            // Find the "Cancel" button in the popup layout
            Button addFilePopButton = popupView.findViewById(R.id.addButtonFilePopup);

            addFilePopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("subjectDetailsActivity", "addFilePopButton.setOnClickListeneronClick:" + popFileName.getText().toString());
                    Log.i("subjectDetailsActivity", "addFilePopButton.setOnClickListeneronClick:" + popFilePath.getText().toString());

                    // Add your file creation logic here
                    String fileName = popFileName.getText().toString();
                    String filePath = popFilePath.getText().toString();

                    // Add your file creation logic here
                    File newFile = File.builder()
                            .name(fileName)
                            .link(filePath)  // Assuming filePath is the link to the file
                            .subject(subject)
                            .build();

                    // Replace the following logic with your actual API call or file handling logic
                    // Amplify.API.mutate(
                    //         ModelMutation.create(newFile),
                    //         successResponse -> Log.i(SUBJECT_DETAILS, "SaveFileAction.onCreate(): File added successfully"),
                    //         failureResponse -> Log.e(SUBJECT_DETAILS, "SaveFileAction.onCreate(): failed with this response" + failureResponse)
                    // );

                    Amplify.API.mutate(
                            ModelMutation.create(newFile),
                            successResponse -> Log.i(SUBJECT_DETAILS, "SaveFileAction.onCreate(): Record added successfully"),//success response
                            failureResponse -> Log.e(SUBJECT_DETAILS, "SaveFileAction.onCreate(): fail d with this response" + failureResponse)// in case we have a failed response
                    );
                    finish();
                    // Close the popup
                    Snackbar.make(findViewById(android.R.id.content), "File Added", Snackbar.LENGTH_SHORT).show();

                    popupWindow.dismiss();
                }
            });

            // Show the popup window
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        });
    }



    private void addNote() {
        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(view -> {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            // Inflate the popup_addnotes.xml
            View popupView = inflater.inflate(R.layout.popup_addnotes, null);

            // Create a new instance of PopupWindow
            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Set background drawable to allow dismissal when clicking outside the popup window
            popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

            // Set focusable true to enable touch events outside of the popup window
            popupWindow.setFocusable(true);

            // Find the "Cancel" button in the popup layout
            Button cancelButton = popupView.findViewById(R.id.cancelButtonNotePopup); // Replace with the actual ID of your "Cancel" button

            // Set an OnClickListener for the "Cancel" button to dismiss the popup
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss(); // Dismiss the popup when the "Cancel" button is clicked
                }
            });

            // Show the popup window
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        });


    }


    private void addRecord() {
        Button addRecordButton = findViewById(R.id.addRecordButton);
        addRecordButton.setOnClickListener(view -> {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            // Inflate the popup_layout.xml
            View popupView = inflater.inflate(R.layout.popup_addrecords, null);

            // Create a new instance of PopupWindow
            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Set background drawable to allow dismissal when clicking outside the popup window
            popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

            // Set focusable true to enable touch events outside of the popup window
            popupWindow.setFocusable(true);

            // Find views from the inflated layout (popupView)
            TextView popAddRecordTitleTextView = popupView.findViewById(R.id.addRecordPopupTitle);
            EditText popRecordLink = popupView.findViewById(R.id.addLinkRecordPopupEditText);
            EditText potRecordTitle = popupView.findViewById(R.id.addTitleRecordPopupEditText);

            // Set the text for popAddRecordTitleTextView
            popAddRecordTitleTextView.setText("Add Your Record to : "+subject.getTitle());

            // Find the "Cancel" button in the popup layout
            Button cancelButton = popupView.findViewById(R.id.cancelButtonRecordPopup);

            // Set an OnClickListener for the "Cancel" button to dismiss the popup
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss(); // Dismiss the popup when the "Cancel" button is clicked
                }
            });

            Button addRecordPopButton = popupView.findViewById(R.id.addButtonRecordPopup);

            addRecordPopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("subjectDetailsActivity", "addRecordPopButton.setOnClickListeneronClick:" + potRecordTitle.getText().toString());
                    Log.i("subjectDetailsActivity", "addRecordPopButton.setOnClickListeneronClick:" + popRecordLink.getText().toString());

                    // Add your record creation logic here
                    Record newRecord=Record.builder()
                            .name(potRecordTitle.getText().toString())
                            .link(popRecordLink.getText().toString())
                            .subject(subject)
                            .build();


                    Amplify.API.mutate(
                            ModelMutation.create(newRecord),
                            successResponse -> Log.i(SUBJECT_DETAILS, "SaveRecordAction.onCreate(): Record added successfully"),//success response
                            failureResponse -> Log.e(SUBJECT_DETAILS, "SaveRecordAction.onCreate(): fail d with this response" + failureResponse)// in case we have a failed response
                    );
                    finish();
                    // Close the popup
                    popupWindow.dismiss();
                }
            });

            // Show the popup window
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        });
    }



    private void goToEditSubject() {
        Button editSubjectButton=(Button) findViewById(R.id.editSubjectButton);
        editSubjectButton.setOnClickListener(view -> {

            Intent goToEditSubjectIntent = new Intent(SubjectDetailsActivity.this,EditSubjectActivity.class );
            goToEditSubjectIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectIDFromIntent);
            startActivity(goToEditSubjectIntent);
        });
    }

    private void addGrade() {
        Button addGradeButton=(Button) findViewById(R.id.addGrandesButton);
        addGradeButton.setOnClickListener(view -> {

            Intent goToAddGradeIntent = new Intent(SubjectDetailsActivity.this,AddGradeActivity.class );
            goToAddGradeIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectIDFromIntent);
            startActivity(goToAddGradeIntent);
        });
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
        List<File> fileList = subject.getFiles();

        return fileList;
    }

    private List<Record> generateRecordList() {
        List<Record> recordList =subject.getRecords();

        return recordList;
    }


}
