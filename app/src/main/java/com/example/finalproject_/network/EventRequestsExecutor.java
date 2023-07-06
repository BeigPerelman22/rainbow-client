package com.example.finalproject_.network;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.network.Requests.DeleteEventRequest;
import com.example.finalproject_.network.Requests.GetEventsRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class EventRequestsExecutor {

    GetEventsRequest getEventsRequest;
    DeleteEventRequest deleteEventRequest;

    public EventRequestsExecutor() {
        getEventsRequest = new GetEventsRequest();
        deleteEventRequest = new DeleteEventRequest();
    }

    public Call<List<EventModel>> fetchEvents() {
        return getEventsRequest.fetch();
    }

    public Call<ResponseBody> deleteEvent(String eventId) {
        return deleteEventRequest.delete(eventId);
    }

}
