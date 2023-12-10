package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.demo.subjectplanner.R;

public class VerifyEmailActivity extends AppCompatActivity {
    public static final String TAG= "VerifyEmailActivity";

    public static final String VERIFY_EMAIL_TAG = "Verify_Email_Tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        Toolbar signUpToolbar = findViewById(R.id.verifyEmailToolbar);
        setSupportActionBar(signUpToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setTitle("");
        setupLoginPageAfterVerifyPageButton();


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

    public void setupLoginPageAfterVerifyPageButton(){
        Button verifyEmailButton = findViewById(R.id.verifyEmailSubmitButton);

        verifyEmailButton.setOnClickListener(V -> {


            Intent goToLoginIntent = new Intent(VerifyEmailActivity.this, LoginActivity.class);


            startActivity(goToLoginIntent);
        });

    }
}