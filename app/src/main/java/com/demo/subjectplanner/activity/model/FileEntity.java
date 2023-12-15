package com.demo.subjectplanner.activity.model;


public class FileEntity {
    private String title;
    private String link;

    public FileEntity(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}