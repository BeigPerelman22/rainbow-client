package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventsResponseModel {

    @SerializedName("items")
    public List<EventModel> events;

}
