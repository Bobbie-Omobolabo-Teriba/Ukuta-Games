package com.example.ukutagamesv1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "platform_table")
public class Platform {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String developer;
    private String launch;

    public Platform(String name, String developer, String launch) {
        this.name = name;
        this.developer = developer;
        this.launch = launch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLaunch() {
        return launch;
    }

    public String getDeveloper() {
        return developer;
    }

}
