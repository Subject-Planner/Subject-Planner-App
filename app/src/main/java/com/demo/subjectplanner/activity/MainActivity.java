package com.demo.subjectplanner.activity;

import static com.demo.subjectplanner.activity.LoginActivity.ID_TAG;

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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Event;
import com.amplifyframework.datastore.generated.model.Grade;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.adapter.HomePageRecyclerViewAdapter;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    public static final String TAG = "SubjectActivity";
    public static final String NUMBER_OF_ABSENTS = "numberOfAbsents";
    public static final String SUBJECT_TITLE_TAG = "subjectTitle";
    List<Subject> subjects;
    Student loggedInStudent;
    public static final String SUBJECT_ID_TAG ="subjectId" ;
    HomePageRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setupHomePageRecyclerView();
        getLoggedUserSubjects();
        addNewSubjectButton();
    }


    @Override
    protected void onResume() {
        super.onResume();

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
        if (id == R.id.nav_logout) {
            // Handle the Grades item click
            Toast.makeText(this, "log out", Toast.LENGTH_LONG).show();
            // Clear user-related data from SharedPreferences
            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
            preferenceEditor.remove(ID_TAG);
            preferenceEditor.apply();

            // Navigate to the login screen
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        if (id == R.id.nav_agenda) {
            // Handle the Agenda item click
            Toast.makeText(this, "Go to Agenda", Toast.LENGTH_LONG).show();
            Intent goToAgenda = new Intent(MainActivity.this, AgendaActivity.class);
            startActivity(goToAgenda);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    public void setupHomePageRecyclerView() {
        RecyclerView homePageRecyclerView = (RecyclerView) findViewById(R.id.homeActivityRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        homePageRecyclerView.setLayoutManager(layoutManager);

        subjects = new ArrayList<>();

        adapter = new HomePageRecyclerViewAdapter( subjects,this);
        homePageRecyclerView.setAdapter(adapter);
    }
    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

    }
    private void addNewSubjectButton() {
        ImageButton addNewSubject = findViewById(R.id.add_subject_button);
        Log.i(TAG, "logged user: " + loggedInStudent);
        addNewSubject.setOnClickListener(view -> {
            if (loggedInStudent == null) {
                Log.i(TAG, "User is null, not starting AddNewSubjectActivity");
                Snackbar.make(findViewById(R.id.drawer_layout), "Login to add subjects", Snackbar.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "Starting AddNewSubjectActivity");
                Intent goToAddNewSubject = new Intent(MainActivity.this, AddNewSubjectActivity.class);
                startActivity(goToAddNewSubject);
            }
        });
    }

private void getLoggedUserSubjects(){
    //subjects=new ArrayList<>();
    String loggedUserId= sharedPreferences.getString(ID_TAG,"");
    Amplify.API.query(
            ModelQuery.get(Student.class, loggedUserId),
            response -> {
                loggedInStudent = response.getData();
                if (loggedInStudent != null) {
                    subjects.clear(); // Clear the existing list before adding new subjects

                    subjects.addAll(loggedInStudent.getSubjects());
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });

                } else {
                    Log.e(TAG, "User Not Found");
                }
            },
            error -> {
                Log.e(TAG, "Error fetching User by ID", error);
            }
    );
}

    }
