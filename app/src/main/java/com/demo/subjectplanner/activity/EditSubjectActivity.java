package com.demo.subjectplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.RecordsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditSubjectActivity extends AppCompatActivity {

    private TextView recordNameTextView;

    private RecyclerView recordsrecyclerView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        Intent callingIntent = getIntent();
        String subjectTilteString = null;

        List<String> recordList = generateRecordList();

        RecyclerView recordsrecyclerView = findViewById(R.id.EditRecordsRecyclerView);
        recordsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecordsRecyclerViewAdapter recordsRecyclerViewAdapter = new RecordsRecyclerViewAdapter(recordList, this);
        recordsrecyclerView.setAdapter(recordsRecyclerViewAdapter);


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



