package com.example.finalproject_.network;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.event_requests.UpdateEventRequestModel;
import com.example.finalproject_.network.Requests.AddEventRequest;
import com.example.finalproject_.network.Requests.DeleteEventRequest;
import com.example.finalproject_.network.Requests.GetEventsRequest;
import com.example.finalproject_.network.Requests.UpdateEventRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class EventRequestsExecutor {

    GetEventsRequest getEventsRequest;
    AddEventRequest addEventRequest;
    UpdateEventRequest updateEventRequest;
    DeleteEventRequest deleteEventRequest;


    public EventRequestsExecutor() {
        getEventsRequest = new GetEventsRequest();
        addEventRequest = new AddEventRequest();
        updateEventRequest = new UpdateEventRequest();
        deleteEventRequest = new DeleteEventRequest();
    }

    public Call<List<EventModel>> fetchEvents() {
        return getEventsRequest.fetch();
    }

    public Call<ResponseBody> deleteEvent(String eventId) {
        return deleteEventRequest.delete(eventId);
    }

    public Call<EventModel> createEvent(EventModel createEventRequest) {
        return addEventRequest.create(createEventRequest);
    }

    public Call<EventModel> updateEvent(EventModel eventRequestModel) {
        return updateEventRequest.update(eventRequestModel);
    }

}
