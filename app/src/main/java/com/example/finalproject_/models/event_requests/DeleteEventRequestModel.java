package com.example.finalproject_.models.event_requests;

import com.google.gson.annotations.SerializedName;

public class DeleteEventRequestModel  {

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
