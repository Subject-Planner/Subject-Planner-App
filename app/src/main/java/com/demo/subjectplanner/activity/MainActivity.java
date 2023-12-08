package com.demo.subjectplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.database.DatabaseSingleton;
import com.demo.subjectplanner.activity.database.SubjectDatabase;

public class MainActivity extends AppCompatActivity {
        public static final String DATABASE_TAG="subjectDatabase";
    SubjectDatabase subjectDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Room Database*/
        subjectDatabase = DatabaseSingleton.getInstance(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        }
        return true;
    }
}