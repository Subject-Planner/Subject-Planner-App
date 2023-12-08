package com.demo.subjectplanner.activity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity

public class Subject {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String images;
    private String records;
    private String files;
    private String notes;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int grades;
    private int numberOfAbsents;

    public Subject() {
    }

    public Subject( String title, String images, String records, String files, String notes, LocalTime startTime, LocalTime endTime, LocalDateTime startDateTime, LocalDateTime endDateTime, int grades, int numberOfAbsents) {

        this.title = title;
        this.images = images;
        this.records = records;
        this.files = files;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.grades = grades;
        this.numberOfAbsents = numberOfAbsents;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }

    public void setNumberOfAbsents(int numberOfAbsents) {
        this.numberOfAbsents = numberOfAbsents;
    }

    public String getTitle() {
        return title;
    }

    public String getImages() {
        return images;
    }

    public String getRecords() {
        return records;
    }

    public String getFiles() {
        return files;
    }

    public String getNotes() {
        return notes;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getGrades() {
        return grades;
    }

    public int getNumberOfAbsents() {
        return numberOfAbsents;
    }
}
