package com.example.finalproject_.network.Requests;

import com.example.finalproject_.interfaces.EventAPIInterface;
import com.example.finalproject_.models.event_requests.TokenRequestModel;
import com.example.finalproject_.network.EventAPIClient;
import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.SharedPreferencesUtils;

public abstract class BasicEventRequest {

    protected EventAPIInterface eventAPIInterface;
    protected String token;
    protected String calendarId;

    public BasicEventRequest() {
        eventAPIInterface = EventAPIClient.getClient().create(EventAPIInterface.class);
        calendarId = SharedPreferencesUtils.getString(MyApplication.getInstance(), "calendar_id", "");
        token = SharedPreferencesUtils.getString(MyApplication.getInstance(), "token", "");
    }

    public <T extends TokenRequestModel> void setTokenToRequest(T requestModel) {
        requestModel.setToken(token);
        requestModel.setCalendarId(calendarId);
    }

}
