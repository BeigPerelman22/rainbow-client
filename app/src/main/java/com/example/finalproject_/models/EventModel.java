package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class EventModel {

    @SerializedName("summary")
    public String description;

    @SerializedName("created")
    public String time;

    @SerializedName("start")
    public StartDate startDate;


    public class StartDate {
        @SerializedName("dateTime")
        private String dateTime;

        public String getDateTime() {
            return dateTime;
        }
    }

    public String getDescription() {
        return description;
    }


    public String getTime() {
        return time;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
