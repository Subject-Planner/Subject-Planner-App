package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.demo.subjectplanner.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupSignUpButton();
    }

    public void setupSignUpButton(){
        Button signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(V -> {


            Intent goToSignUpIntent = new Intent(LoginActivity.this, SignupActivity.class);

            startActivity(goToSignUpIntent);
        });

    }
}