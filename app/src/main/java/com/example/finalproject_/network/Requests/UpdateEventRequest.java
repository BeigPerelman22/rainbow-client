package com.example.finalproject_.network.Requests;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.event_requests.UpdateEventRequestModel;

import retrofit2.Call;

public class UpdateEventRequest extends BasicEventRequest{

    public Call<EventModel> update(EventModel eventRequestModel) {
        return eventAPIInterface.updateEvent(eventRequestModel);
    }

}
