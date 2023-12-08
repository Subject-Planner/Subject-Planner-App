package com.demo.subjectplanner.activity.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.demo.subjectplanner.activity.dao.SubjectDao;
import com.demo.subjectplanner.activity.model.Subject;

@Database(entities = {Subject.class}, version = 1)
public abstract  class SubjectDatabase extends RoomDatabase {
    public abstract SubjectDao subjectDao();
}
