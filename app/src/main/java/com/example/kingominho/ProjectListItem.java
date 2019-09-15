package com.example.kingominho;

import android.widget.Button;

public class ProjectListItem {

    private String title;
    private String duration;
    private String link;

    public ProjectListItem(String title, String duration, String link) {
        this.title = title;
        this.duration = duration;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
