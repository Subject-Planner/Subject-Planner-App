package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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


        init();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        /*Room Database*/
        subjectDatabase = DatabaseSingleton.getInstance(getApplicationContext());
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //setupLogin();
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
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_grades) {
            // Handle the Grades item click
            Toast.makeText(this, "Go to Grades", Toast.LENGTH_LONG).show();
            Intent goToGrades = new Intent(MainActivity.this, AddGradeActivity.class);
            startActivity(goToGrades);
        }
        if (id == R.id.nav_login) {
            // Handle the Grades item click
            Toast.makeText(this, "Go to Login", Toast.LENGTH_LONG).show();
            Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        }
        if (id == R.id.nav_calender) {
            // Handle the Grades item click
            Toast.makeText(this, "Go to Calendar", Toast.LENGTH_LONG).show();
            Intent goToCalendar = new Intent(MainActivity.this, Calendar.class);
            startActivity(goToCalendar);
        }
        if (id == R.id.nav_profile) {
            // Handle the Grades item click
            Toast.makeText(this, "Go to Profile", Toast.LENGTH_LONG).show();
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(goToProfile);
        }

        // Add more cases for other items if needed...

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.Agenda){
            Toast.makeText(this,"go to Calendar",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Recordings){
            Toast.makeText(this,"go to Recordings",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.Calendar){
            Toast.makeText(this,"go to Calendar",Toast.LENGTH_LONG).show();
            Intent goToCalender= new Intent(MainActivity.this,Calendar.class);
            startActivity(goToCalender);
        }
        if(id==R.id.Grades){
            Toast.makeText(this,"go to Grades",Toast.LENGTH_LONG).show();
            Intent goToGrades= new Intent(MainActivity.this, AddGradeActivity.class);
            startActivity(goToGrades);
        }
        return true;
    }

//    void setupLogin(){
//        TextView login = findViewById(R.id.loginTextView);
//        login.setOnClickListener((V -> {
//
//
//            Intent goTologinIntent = new Intent(MainActivity.this, LoginActivity.class);
//
//            startActivity(goTologinIntent);
//        }));
//}


    public void setupHomePageRecyclerView() {
        RecyclerView homePageRecyclerView = (RecyclerView) findViewById(R.id.homeActivityRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        homePageRecyclerView.setLayoutManager(layoutManager);

        subjects = new ArrayList<>();
//        subjects = new ArrayList<>();
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

//        add new subject
        ImageButton addNewSubject= findViewById(R.id.add_subject_button);
        addNewSubject.setOnClickListener(view -> {
            Intent goToAddNewSubject=new Intent( MainActivity.this,AddNewSubjectActivity.class);
            startActivity(goToAddNewSubject);
        });
    }
}