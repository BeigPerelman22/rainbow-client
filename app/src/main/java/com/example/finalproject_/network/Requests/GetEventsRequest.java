package com.example.finalproject_.network.Requests;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.event_requests.FetchEventsRequestModel;

import java.util.List;

import retrofit2.Call;

public class GetEventsRequest extends BasicEventRequest {

    public GetEventsRequest() {
        super();
    }

    public Call<List<EventModel>> fetch() {
        FetchEventsRequestModel fetchEventsRequest = new FetchEventsRequestModel();
        setTokenToRequest(fetchEventsRequest);
        return eventAPIInterface.getEvents(fetchEventsRequest);
    }
}
