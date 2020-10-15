package com.example.team98;

import androidx.appcompat.app.AppCompatActivity;


public class catmodel{
    String name;
    String location;

    public catmodel(String name, String location)
    {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
