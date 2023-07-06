package com.example.finalproject_.models.event_requests;

import com.google.gson.annotations.SerializedName;

public class TokenRequestModel {

    @SerializedName("calendarId")
    private String calendarId;

    @SerializedName("token")
    private String token;

    public TokenRequestModel() {
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
