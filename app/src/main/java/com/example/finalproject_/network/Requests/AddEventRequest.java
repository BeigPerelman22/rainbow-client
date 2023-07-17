package com.example.finalproject_.network.Requests;


import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.event_requests.CreateEventRequestModel;

import retrofit2.Call;

public class AddEventRequest extends BasicEventRequest{

    public Call<EventModel> create(CreateEventRequestModel createEventRequestModel) {
        return eventAPIInterface.createEvent(createEventRequestModel);
    }

}
