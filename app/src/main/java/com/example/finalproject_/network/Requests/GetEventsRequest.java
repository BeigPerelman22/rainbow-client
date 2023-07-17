package com.example.finalproject_.network.Requests;

import com.example.finalproject_.models.EventModel;

import java.util.List;

import retrofit2.Call;

public class GetEventsRequest extends BasicEventRequest {

    public GetEventsRequest() {
        super();
    }

    public Call<List<EventModel>> fetch() {
        return eventAPIInterface.getEvents();
    }
}
