package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.demo.subjectplanner.R;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";

    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";


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