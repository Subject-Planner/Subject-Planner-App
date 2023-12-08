package com.demo.subjectplanner.activity.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.demo.subjectplanner.activity.model.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    public void insertSubject(Subject subject);

    @Query("select * from Subject")
    public List<Subject> findAll();

    @Query("select * from Subject where title = :title")
    Subject findByTitle(String title);
}
