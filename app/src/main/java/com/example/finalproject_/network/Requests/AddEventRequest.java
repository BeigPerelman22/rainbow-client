package com.example.finalproject_.network.Requests;


import com.example.finalproject_.models.EventModel;

import retrofit2.Call;

public class AddEventRequest extends BasicEventRequest{

    public Call<EventModel> create(EventModel createEventRequestModel) {
        return eventAPIInterface.createEvent(createEventRequestModel);
    }

}
