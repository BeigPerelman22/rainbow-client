package com.example.finalproject_.network.Requests;

import com.example.finalproject_.models.event_requests.DeleteEventRequestModel;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DeleteEventRequest extends BasicEventRequest{

    public DeleteEventRequest() {
        super();
    }

    public Call<ResponseBody> delete(String eventId) {
        DeleteEventRequestModel deleteEventRequestModel = new DeleteEventRequestModel();
        setTokenToRequest(deleteEventRequestModel);
        deleteEventRequestModel.setId(eventId);
        return eventAPIInterface.deleteEvent(deleteEventRequestModel);
    }
}
