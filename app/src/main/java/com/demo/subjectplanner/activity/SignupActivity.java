package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;
import com.demo.subjectplanner.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";
    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";
    List<Student> users = new ArrayList<>();
    Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar signUpToolbar = findViewById(R.id.signUpToolbar);
        setSupportActionBar(signUpToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setTitle("");
        setupVerifyEmailPageButton();
        queryingUsersFromDB();
        setupSignupButton();
    }

    private void queryingUsersFromDB() {
        users = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Student.class),
                successResponse -> {
                    Log.i(TAG, "Reading users successfully" + successResponse.getData().toString());
                    users.clear();
                    for (Student user : successResponse.getData()) {
                        users.add(user);
                    }

                    runOnUiThread(this::setupSignupButton);
                },
                failureResponse -> {
                    Log.i(TAG, "Fail to read a users");
                }
        );
    }

    private void setupSignupButton() {

        signup = findViewById(R.id.signUpSubmitButton);
        EditText usernameEditText = findViewById(R.id.signUpFullNameEditText);
        EditText emailEditText = findViewById(R.id.signUpEmailEditText);
        EditText passwordEditText = findViewById(R.id.signUpPasswordEditText);

        signup.setOnClickListener(b -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Snackbar.make(findViewById(R.id.signupActivity), "Please enter all fields", Snackbar.LENGTH_SHORT).show();
            } else {
                saveUserToDB(username, email, password);
                Intent goToLoginActivity = new Intent(SignupActivity.this , LoginActivity.class );
                startActivity(goToLoginActivity);
            }
        });
    }
    private void saveUserToDB(String username , String email , String password){

        for (Student user  : users) {
            if (user.getName().equalsIgnoreCase(username) && user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)){
                Snackbar.make(findViewById(R.id.signupActivity), "You are already registered", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }
        Student student = Student.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(student),
                successResponse -> Log.i(TAG, "user signed up successfully"),
                failureResponse -> Log.e(TAG, "user failed to register" + failureResponse)

        );

        Snackbar.make(findViewById(R.id.signupActivity), "Task Saved Successfully", Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public void setupVerifyEmailPageButton(){
        Button signUpButton = findViewById(R.id.signUpSubmitButton);

        signUpButton.setOnClickListener(V -> {


            Intent goToVerifyEmailIntent = new Intent(SignupActivity.this, VerifyEmailActivity.class);


            startActivity(goToVerifyEmailIntent);
        });

    }
}