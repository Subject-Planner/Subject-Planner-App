package com.demo.subjectplanner.activity.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseSingleton {
    private static SubjectDatabase instance;
    public static final String DATABASE_TAG="subjectDatabase";

    public static SubjectDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseSingleton.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    SubjectDatabase.class, DATABASE_TAG)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }}
