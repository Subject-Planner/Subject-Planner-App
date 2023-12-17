package com.demo.subjectplanner.activity;
import static com.demo.subjectplanner.activity.MainActivity.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.FileAdapter;
import com.demo.subjectplanner.activity.adapter.GradeAdapter;
import com.demo.subjectplanner.activity.adapter.RecordsRecyclerViewAdapter;
import com.demo.subjectplanner.activity.model.FileEntity;

import java.util.ArrayList;
import java.util.List;

public class SubjectDetailsActivity extends AppCompatActivity {
    public static final String SUBJECT_TITLE = "subjectTitle";
    TextView subjectNameTextView;
    TextView numberOfAbsentsTextView;
    TextView daysTextView;
    TextView eventsTextView;
    TextView notesTextView;
    RecyclerView recyclerView;
    List<Subject> subjects;
    Subject subject;
    String subjectTitleString=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject_details);

        Intent callingIntent = getIntent();
         subjectTitleString = callingIntent.getStringExtra(MainActivity.SUBJECT_TITLE_TAG);

        List<FileEntity> fileList = generateFileList();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        FileAdapter adapter = new FileAdapter(fileList, this);
        recyclerView.setAdapter(adapter);


        retrieveAllSubjects(subjectList -> {
            List<String> recordList = generateRecordList();

            RecyclerView recordsrecyclerView = findViewById(R.id.SubjectRecordsRecyclerView);

            recordsrecyclerView.setLayoutManager(new LinearLayoutManager(SubjectDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            RecordsRecyclerViewAdapter recordsRecyclerViewAdapter = new RecordsRecyclerViewAdapter(recordList, SubjectDetailsActivity.this);
            recordsrecyclerView.setAdapter(recordsRecyclerViewAdapter);

            subject = getSubjectByTitle(subjectTitleString, subjectList);

            RecyclerView gradeRecyclerView = findViewById(R.id.grecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(SubjectDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
            gradeRecyclerView.setLayoutManager(layoutManager);
            GradeAdapter gradeAdapter = new GradeAdapter(subject.getGrades(), SubjectDetailsActivity.this);
            gradeRecyclerView.setAdapter(gradeAdapter);



            subjectNameTextView = findViewById(R.id.subjectTitleText);
            subjectNameTextView.setText(subject.getTitle());

            numberOfAbsentsTextView = findViewById(R.id.numberOfAbsents);
            numberOfAbsentsTextView.setText("Number of Absents : "+subject.getNumberOfAbsents());

            daysTextView = findViewById(R.id.days);
            daysTextView.setText(concatenateDaysEnum(subject.getDays()));

            eventsTextView = findViewById(R.id.events);
            eventsTextView.setText(concatenateEventDays(subject.getEvents()));

            notesTextView=findViewById(R.id.notes);
            notesTextView.setText(createNumberedList(subject.getNotes()));

        });


        addGrade();
        goToEditSubject();
        addRecord();
        addNote();
        addFile();
        addEvent();

//        createPopUpWindow();
    }

    private void addEvent() {
        Button addEventButton=(Button) findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(view -> {

//            Intent goToAddEventIntent = new Intent(SubjectDetailsActivity.this,EditSubjectActivity.class );
//            goToAddEventIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectTitleString);
//            startActivity(goToAddEventIntent);
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
            Button cancelButton = popupView.findViewById(R.id.cancelButtonFilePopup); // Replace with the actual ID of your "Cancel" button

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

            // Find the "Cancel" button in the popup layout
            Button cancelButton = popupView.findViewById(R.id.cancelButtonRecordPopup); // Replace with the actual ID of your "Cancel" button

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


    private void goToEditSubject() {
        Button editSubjectButton=(Button) findViewById(R.id.editSubjectButton);
        editSubjectButton.setOnClickListener(view -> {

            Intent goToEditSubjectIntent = new Intent(SubjectDetailsActivity.this,EditSubjectActivity.class );
            goToEditSubjectIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectTitleString);
            startActivity(goToEditSubjectIntent);
        });
    }

    private void addGrade() {
        Button addGradeButton=(Button) findViewById(R.id.addGrandesButton);
        addGradeButton.setOnClickListener(view -> {

            Intent goToAddGradeIntent = new Intent(SubjectDetailsActivity.this,AddGradeActivity.class );
            goToAddGradeIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectTitleString);
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

    private void retrieveAllSubjects(OnSubjectsRetrievedListener listener) {
        subjects = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Subject.class),
                success -> {
                    Log.i(TAG, "Retrieved Subjects Successfully!");
                    subjects.clear();
                    for (Subject databaseSubject : success.getData()) {
                        subjects.add(databaseSubject);
                    }
                    runOnUiThread(() -> {
                        if (listener != null) {
                            listener.onSubjectsRetrieved(subjects);
                        }
                    });
                },
                failure -> Log.e(TAG, "Failed to retrieve subjects. Error: " + failure.toString())
        );
    }

    private interface OnSubjectsRetrievedListener {
        void onSubjectsRetrieved(List<Subject> subjects);
    }

    private Subject getSubjectByTitle(String title, List<Subject> subjectList) {
        for (Subject subject : subjectList) {
            if (subject.getTitle().equals(title)) {
                return subject;
            }
        }
        return null;
    }

    private List<FileEntity> generateFileList() {
        List<FileEntity> fileList = new ArrayList<>();
        fileList.add(new FileEntity("AI", "https://elcom-team.com/Subjects/%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A/%D8%AA%D9%84%D8%AE%D9%8A%D8%B5%20%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A%20-%20%D8%AD%D9%85%D8%B2%D8%A9%20%D8%A7%D8%B3%D9%85%D8%A7%D8%B9%D9%8A%D9%84.pdf"));
        fileList.add(new FileEntity("PIC", "https://elcom-hu.com/Subjects/Mech/4th-and-5th-year/%D9%85%D8%B9%D8%A7%D9%84%D8%AC%D8%A7%D8%AA-%D9%88%D9%85%D8%AA%D8%AD%D9%83%D9%85%D8%A7%D8%AA-%D8%AF%D9%82%D9%8A%D9%82%D8%A9/%D8%AF%D9%81%D8%AA%D8%B1-%D8%A8%D9%8A%D9%83-%D8%AF.%D8%A7%D8%B3%D9%85%D8%A7%D8%A1-%D8%A7%D9%84%D8%AA%D9%85%D9%8A%D9%85%D9%8A"));
        // Add more files as needed
        fileList.add(new FileEntity("AsI", "https://elcom-team.com/Subjects/%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A/%D8%AA%D9%84%D8%AE%D9%8A%D8%B5%20%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A%20-%20%D8%AD%D9%85%D8%B2%D8%A9%20%D8%A7%D8%B3%D9%85%D8%A7%D8%B9%D9%8A%D9%84.pdf"));
        fileList.add(new FileEntity("AeI", "https://elcom-team.com/Subjects/%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A/%D8%AA%D9%84%D8%AE%D9%8A%D8%B5%20%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A%20-%20%D8%AD%D9%85%D8%B2%D8%A9%20%D8%A7%D8%B3%D9%85%D8%A7%D8%B9%D9%8A%D9%84.pdf"));

        return fileList;
    }

    private List<String> generateRecordList() {
        List<String> recordList = new ArrayList<>();
        recordList.add("Record 1" );
        recordList.add("Record 2" );
        recordList.add("Record 3" );
        recordList.add("Record 4" );
        recordList.add("Record 5" );
        recordList.add("Record 6" );
        recordList.add("Record 7" );
        recordList.add("Record 8" );
        return recordList;
    }
}
