package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;
import com.demo.subjectplanner.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "login_Activity_TAG";
    public static final String ID_TAG = "userId";
    Button loginButton;
    List<Student> users = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queryingUsersFromDB();
        setupLoginButton();
        setupSignUpButton();
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

                    runOnUiThread(this::setupLoginButton);
                },
                failureResponse -> {
                    Log.i(TAG, "Fail to read a users");
                }
        );
    }

    private void setupLoginButton() {

//        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(b -> {
            String loginEmail = ((EditText) findViewById(R.id.loginEmailEditText)).getText().toString();
            String loginPassword = ((EditText) findViewById(R.id.loginPasswordEditText)).getText().toString();

            for (Student user : users) {
                if (user.getEmail().equalsIgnoreCase(loginEmail.trim()) && user.getPassword().equalsIgnoreCase(loginPassword)) {
                    Snackbar.make(findViewById(R.id.loginActivity), "Welcome" + " " + user.getName(), Snackbar.LENGTH_LONG).show();

                    SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
                    preferenceEditor.putString(ID_TAG, user.getId());
                    preferenceEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    return;
                }
            }

            Snackbar.make(findViewById(R.id.loginActivity), "Failed to login", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void setupSignUpButton() {
        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(V -> {
            Intent goToSignUpIntent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(goToSignUpIntent);
        });
    }
}
