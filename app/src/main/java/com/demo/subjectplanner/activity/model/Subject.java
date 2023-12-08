package com.demo.subjectplanner.activity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity

public class Subject {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String images;
    private String records;
    private String files;
    private String notes;
    private Date startTime;
    private Date endTime;
    private Date startDate;
    private Date endDate;
    private int grades;
    private int numberOfAbsents;

    public Subject() {
    }

    public Subject(String title, String images, String records, String files, String notes, Date startTime, Date endTime, Date startDate, Date endDate, int grades, int numberOfAbsents) {
        this.title = title;
        this.images = images;
        this.records = records;
        this.files = files;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDate = startDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDate = endDateTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartDateTime() {
        return startDate;
    }

    public Date getEndDateTime() {
        return endDate;
    }

    public int getGrades() {
        return grades;
    }

    public int getNumberOfAbsents() {
        return numberOfAbsents;
    }
}
