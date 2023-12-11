package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.HomePageRecyclerViewAdapter;
import com.demo.subjectplanner.activity.database.DatabaseSingleton;
import com.demo.subjectplanner.activity.database.SubjectDatabase;
import com.demo.subjectplanner.activity.model.Subject;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        public static final String DATABASE_TAG="subjectDatabase";
    SubjectDatabase subjectDatabase;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    List<Subject> subjects = null;
    HomePageRecyclerViewAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*Room Database*/
        subjectDatabase = DatabaseSingleton.getInstance(getApplicationContext());
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id==R.id.Agenda){
//            Toast.makeText(this,"go to Agenda",Toast.LENGTH_LONG).show();
//        }
//        if(id==R.id.Recordings){
//            Toast.makeText(this,"go to Recordings",Toast.LENGTH_LONG).show();
//        }
//        if(id==R.id.Calendar){
//            Toast.makeText(this,"go to Calendar",Toast.LENGTH_LONG).show();
//        }
//        if(id==R.id.Grades){
//            Toast.makeText(this,"go to Grades",Toast.LENGTH_LONG).show();
//        }
//        return true;
//    }

    public void setupHomePageRecyclerView() {
        RecyclerView homePageRecyclerView = (RecyclerView) findViewById(R.id.homeActivityRecylerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        homePageRecyclerView.setLayoutManager(layoutManager);

        subjects = new ArrayList<>();
        Subject subject = new Subject();
        subjectDatabase.subjectDao().insertSubject(subject);
        adapter = new HomePageRecyclerViewAdapter(subjects ,this);
        homePageRecyclerView.setAdapter(adapter);
    }
}