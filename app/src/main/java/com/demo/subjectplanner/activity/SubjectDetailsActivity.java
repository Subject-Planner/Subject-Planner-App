package com.demo.subjectplanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.subjectplanner.R;

public class SubjectDetailsActivity extends AppCompatActivity {
    TextView subjectNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        Intent callingIntent = getIntent();
        String subjectTilteString = null;


        if(callingIntent != null){
            subjectTilteString = callingIntent.getStringExtra(MainActivity.SUBJECT_TITLE_TAG);


             subjectNameTextView = (TextView) findViewById(R.id.subjectTitleText);


            if (subjectTilteString != null){
                subjectNameTextView.setText(subjectTilteString);
            }else {
                subjectNameTextView.setText("Subject Unknown");
            }


        }
    }
}