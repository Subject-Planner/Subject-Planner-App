package com.demo.subjectplanner.activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.FileAdapter;
import com.demo.subjectplanner.activity.model.FileEntity;

import java.util.ArrayList;
import java.util.List;

public class SubjectDetailsActivity extends AppCompatActivity {
    TextView subjectNameTextView;
    RecyclerView recyclerViewl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        Intent callingIntent = getIntent();
        String subjectTilteString = null;

        List<FileEntity> fileList = generateFileList();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        FileAdapter adapter = new FileAdapter(fileList, this);
        recyclerView.setAdapter(adapter);

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
    private List<FileEntity> generateFileList() {
        List<FileEntity> fileList = new ArrayList<>();
        fileList.add(new FileEntity("AI", "https://elcom-team.com/Subjects/%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A/%D8%AA%D9%84%D8%AE%D9%8A%D8%B5%20%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A%20-%20%D8%AD%D9%85%D8%B2%D8%A9%20%D8%A7%D8%B3%D9%85%D8%A7%D8%B9%D9%8A%D9%84.pdf"));
        fileList.add(new FileEntity("PIC", "https://elcom-hu.com/Subjects/Mech/4th-and-5th-year/%D9%85%D8%B9%D8%A7%D9%84%D8%AC%D8%A7%D8%AA-%D9%88%D9%85%D8%AA%D8%AD%D9%83%D9%85%D8%A7%D8%AA-%D8%AF%D9%82%D9%8A%D9%82%D8%A9/%D8%AF%D9%81%D8%AA%D8%B1-%D8%A8%D9%8A%D9%83-%D8%AF.%D8%A7%D8%B3%D9%85%D8%A7%D8%A1-%D8%A7%D9%84%D8%AA%D9%85%D9%8A%D9%85%D9%8A"));
        // Add more files as needed
        fileList.add(new FileEntity("AsI", "https://elcom-team.com/Subjects/%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A/%D8%AA%D9%84%D8%AE%D9%8A%D8%B5%20%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A%20-%20%D8%AD%D9%85%D8%B2%D8%A9%20%D8%A7%D8%B3%D9%85%D8%A7%D8%B9%D9%8A%D9%84.pdf"));
        fileList.add(new FileEntity("AeI", "https://elcom-team.com/Subjects/%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A/%D8%AA%D9%84%D8%AE%D9%8A%D8%B5%20%D8%B0%D9%83%D8%A7%D8%A1%20%D8%A7%D8%B5%D8%B7%D9%86%D8%A7%D8%B9%D9%8A%20-%20%D8%AD%D9%85%D8%B2%D8%A9%20%D8%A7%D8%B3%D9%85%D8%A7%D8%B9%D9%8A%D9%84.pdf"));

        return fileList;
    }

}