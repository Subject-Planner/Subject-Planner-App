package com.demo.subjectplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.File;
import com.amplifyframework.datastore.generated.model.Record;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.EditFilesRecyclerViewAdapter;
import com.demo.subjectplanner.activity.adapter.EditRecordsRecyclerViewAdapter;
import com.demo.subjectplanner.activity.adapter.FileAdapter;
import com.demo.subjectplanner.activity.adapter.GradeAdapter;
import com.demo.subjectplanner.activity.adapter.RecordsRecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class EditSubjectActivity extends AppCompatActivity {
public static final String TAG="EditSubjectActivity";
public static final String SUBJECT_ID_TAG="SUBJECT_ID_TAG";
public static final String SUBJECT_ID_TAG2="SUBJECT_ID_TAG2";
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
    Spinner noteSpinner;
    String selectedNote;
    RecyclerView editFileRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        retrieveSubject();
        goToMain();
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
                            deleteNote();
                            EditEvent();
                            decreaseAbsentBy1();
                            goBackToDetails();
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

    private void EditEvent() {
        Button addEventButton=(Button) findViewById(R.id.editEventButton);
        addEventButton.setOnClickListener(view -> {

            Intent goToAddEventIntent = new Intent(EditSubjectActivity.this,AgendaActivity.class );
            goToAddEventIntent.putExtra(SubjectDetailsActivity.SUBJECT_TITLE, subjectIDFromIntent);
            startActivity(goToAddEventIntent);
        });
    }

    private void deleteNote() {

            Button editNoteButton = findViewById(R.id.editNoteButton);
        editNoteButton.setOnClickListener(view -> {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the popup_addnotes.xml
                View popupView = inflater.inflate(R.layout.popup_editnotes, null);

                // Create a new instance of PopupWindow
                PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
            noteSpinner = popupView.findViewById(R.id.editNoteSpinner);

            ArrayList<String> subjectNotes = new ArrayList<>();
            Log.i("saif", "setupNotesSpinner: subject notes "+subjectNotes);
            for (String note :subjectToEdit.getNotes() ) {
                subjectNotes.add(note);

            }
            Log.i("saif", "setupNotesSpinner: subject notes after "+subjectNotes);


            runOnUiThread(() ->
            {
                noteSpinner.setAdapter(new ArrayAdapter<>(
                        this,
                        (android.R.layout.simple_spinner_item),
                        subjectNotes

                ));
            });
          //  setupNotesSpinner();
                // Set background drawable to allow dismissal when clicking outside the popup window
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

                // Set focusable true to enable touch events outside of the popup window
                popupWindow.setFocusable(true);

                // Find the "Cancel" button in the popup layout
                Button cancelButton = popupView.findViewById(R.id.cancelButtonEditNotePopup);

                // Set an OnClickListener for the "Cancel" button to dismiss the popup
                cancelButton.setOnClickListener(v -> popupWindow.dismiss());

                // Find views from the inflated layout (popupView)



                // Find the "Add Note" button in the popup layout
                ImageButton deleteNotePopupButton = popupView.findViewById(R.id.deleteButtonNotePopup);

            deleteNotePopupButton.setOnClickListener(v -> {
                    // Add your note creation logic here
                    String noteContent = noteSpinner.getSelectedItem().toString();

                    // Assuming you have a Subject instance (replace subject with your actual instance)
                    // and you want to associate the note string with the subject
                    subjectToEdit.getNotes().remove(noteSpinner.getSelectedItem());

                    // Update the subject with the new note
                    Amplify.API.mutate(
                            ModelMutation.update(subjectToEdit),
                            successResponse -> Log.i(TAG, "Note deleted successfully"),
                            failureResponse -> Log.e(TAG, "Failed to delete note: " + failureResponse)
                    );

                    // Close the popup
                    Snackbar.make(findViewById(android.R.id.content), "Note Added", Snackbar.LENGTH_SHORT).show();
                    startActivity(getIntent());
                    popupWindow.dismiss();
                });

                // Show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            });
        }



    void retrieveAllSubjectInfo() {
        List<File> fileList = generateFileList();

        editFileRecyclerView = findViewById(R.id.editFileRecyclerView);
        editFileRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        EditFilesRecyclerViewAdapter adapter = new EditFilesRecyclerViewAdapter(fileList, this);
        editFileRecyclerView.setAdapter(adapter);


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
        subjectNameTextView.setText("Edit "+subjectToEdit.getTitle());

         numberOfAbsentsTextView = findViewById(R.id.editNumberOfAbsents);
        numberOfAbsentsTextView.setText(" Remaining Absents : "+subjectToEdit.getNumberOfAbsents());



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

    void decreaseAbsentBy1() {
        Button decreaseAbsent = findViewById(R.id.editNumberOfAbsentsButton);
        decreaseAbsent.setOnClickListener(view -> {
            if (subjectToEdit.getNumberOfAbsents() > 0) {
                int newNumberOfAbsents = subjectToEdit.getNumberOfAbsents() - 1;

                // Create a new Subject instance with the updated numberOfAbsents
                Subject updatedSubject = Subject.builder()
                        .title(subjectToEdit.getTitle())
                        .id(subjectToEdit.getId())
                        .startDate(subjectToEdit.getStartDate())
                        .endDate(subjectToEdit.getEndDate())
                        .images(subjectToEdit.getImages())
                        .notes(subjectToEdit.getNotes())
                        .numberOfAbsents(newNumberOfAbsents)
                        .days(subjectToEdit.getDays())
                        .studentPerson(subjectToEdit.getStudentPerson())
                        .build();


                Amplify.API.mutate(
                        ModelMutation.update(updatedSubject),
                        success -> {
                            // Successfully updated the numberOfAbsents
                            // Perform any necessary UI updates
                            runOnUiThread(() -> {
                                // Update the UI or perform any other necessary actions with the new value

                                // For example, if you have a TextView to display the updated value:
                                TextView absentsTextView = findViewById(R.id.editNumberOfAbsents);
                                absentsTextView.setText(" Remaining Absents : "+ newNumberOfAbsents);
                            });
                        },
                        error -> Log.e("Amplify", "Error updating numberOfAbsents", error)
                );
            } else {
                // Optionally, handle the case where the number of absents is already 0
                Toast.makeText(this, "Number of absents is already 0", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void goBackToDetails(){
        Button goBackToDetails =findViewById(R.id.SaveEditSubjectButton);
        goBackToDetails.setOnClickListener(view -> {

            Intent goToAddEventIntent = new Intent(EditSubjectActivity.this,SubjectDetailsActivity.class );
            goToAddEventIntent.putExtra(EditSubjectActivity.SUBJECT_ID_TAG2, subjectIDFromIntent);
            startActivity(goToAddEventIntent);
        });

    }
    private void goToMain() {
        ImageView editSubjectButton=findViewById(R.id.editToHome);
        editSubjectButton.setOnClickListener(view -> {

            Intent goToEditSubjectIntent = new Intent(EditSubjectActivity.this,MainActivity.class );

            startActivity(goToEditSubjectIntent);
        });
    }




    private List<File> generateFileList() {
        List<File> fileList = subjectToEdit.getFiles();

        return fileList;
    }

    private List<Record> generateRecordList() {
        List<Record> recordList =subjectToEdit.getRecords();

        return recordList;
    }

    private void setupNotesSpinner(){
        noteSpinner = findViewById(R.id.editNoteSpinner);

        ArrayList<String> subjectNotes = new ArrayList<>();
        Log.i("saif", "setupNotesSpinner: subject notes "+subjectNotes);
        for (String note :subjectToEdit.getNotes() ) {
            subjectNotes.add(note);

        }
        Log.i("saif", "setupNotesSpinner: subject notes after "+subjectNotes);


        runOnUiThread(() ->
        {
            noteSpinner.setAdapter(new ArrayAdapter<>(
                    this,
                    (android.R.layout.simple_spinner_item),
                    subjectNotes

            ));
        });
    }

}



