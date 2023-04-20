package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class EventModel {

    @SerializedName("summary")
    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
