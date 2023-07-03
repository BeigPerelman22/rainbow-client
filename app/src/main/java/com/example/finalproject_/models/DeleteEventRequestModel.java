package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

public class DeleteEventRequestModel extends TokenRequestModel {

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
