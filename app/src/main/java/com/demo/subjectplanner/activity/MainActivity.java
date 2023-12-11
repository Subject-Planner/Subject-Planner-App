package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.HomePageRecyclerViewAdapter;
import com.demo.subjectplanner.activity.database.DatabaseSingleton;
import com.demo.subjectplanner.activity.database.SubjectDatabase;
import com.demo.subjectplanner.activity.model.Subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        public static final String DATABASE_TAG="subjectDatabase";
    SubjectDatabase subjectDatabase;

    List<Subject> subjects = null;
    HomePageRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupHomePageRecyclerView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        subjects.clear();
        subjects.addAll(subjectDatabase.subjectDao().findAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.Agenda){
            Toast.makeText(this,"go to Agenda",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Recordings){
            Toast.makeText(this,"go to Recordings",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Calendar){
            Toast.makeText(this,"go to Calendar",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Grades){
            Toast.makeText(this,"go to Grades",Toast.LENGTH_LONG).show();
            Intent goToGrades= new Intent(MainActivity.this, AddGradeActivity.class);
            startActivity(goToGrades);
        }
        return true;
    }

    public void setupHomePageRecyclerView() {
        RecyclerView homePageRecyclerView = (RecyclerView) findViewById(R.id.homeActivityRecylerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

    //    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        homePageRecyclerView.setLayoutManager(layoutManager);

        subjects = new ArrayList<>();
//        Subject subject = new Subject();
//        subject.setTitle("English");
//        subjects = new ArrayList<>();
//        Subject subject2 = new Subject();
//        subject2.setTitle("Life Skills");
//        subjects = new ArrayList<>();
//        Subject subject3 = new Subject();
//        subject3.setTitle("Java");
//        subjectDatabase.subjectDao().insertSubject(subject);
//        subjectDatabase.subjectDao().insertSubject(subject2);
//        subjectDatabase.subjectDao().insertSubject(subject3);
        adapter = new HomePageRecyclerViewAdapter(subjects ,this);
        homePageRecyclerView.setAdapter(adapter);
    }
    private void init() {
        /*Room Database*/
        subjectDatabase = DatabaseSingleton.getInstance(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //add new subject
        ImageButton addNewSubject= findViewById(R.id.add_subject_button);
        addNewSubject.setOnClickListener(view -> {
            Intent goToAddNewSubject=new Intent( MainActivity.this,AddNewSubjectActivity.class);
            startActivity(goToAddNewSubject);
        });
    }
}