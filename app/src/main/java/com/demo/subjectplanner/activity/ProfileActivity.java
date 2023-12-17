package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;
import com.demo.subjectplanner.R;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private Student student;
    public static final String TAG = "profile activity";

    TextView usernameTextView;
    TextView emailTextView;

    TextView usernameTextViewTopOfPage;

    TextView emailTextViewTopPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString(LoginActivity.ID_TAG, "Default studentId");

        getUserById(userId);
        goToCalender();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getUserById(String userId){
        Amplify.API.query(
                ModelQuery.get(Student.class, userId),
                response -> {
                    if (response.hasData()) {
                        student = response.getData();
                        if (student != null) {
                            Log.i(TAG, "Student Name: " + student.getName());
                            changeUiTextViewProfilePage(student);
                        } else {
                            Log.e(TAG, "Student not found");
                        }
                    } else {
                        Log.e(TAG, "No data in the response");
                    }
                },
                error -> {
                    Log.e(TAG, "Error fetching STUDENT by ID", error);
                }
        );
    }

    private void changeUiTextViewProfilePage(Student student) {
        runOnUiThread(() -> {
            usernameTextViewTopOfPage = findViewById(R.id.usernameReplacedTextView0);
            emailTextViewTopPage = findViewById(R.id.emailReplacedTextView0);
            usernameTextView = findViewById(R.id.usernameReplacedTextView);
            emailTextView = findViewById(R.id.emailReplacedTextView);


            usernameTextViewTopOfPage.setText(student.getName());
            emailTextViewTopPage.setText(student.getEmail());
            usernameTextView.setText(student.getName());
            emailTextView.setText(student.getEmail());
        });
    }

    private void goToCalender(){
        ImageView calandar = findViewById(R.id.profileBioIcon);
        calandar.setOnClickListener(b -> {
            Intent goToCalender = new Intent(ProfileActivity.this , Calendar.class);
            startActivity(goToCalender);
        });

    }

}