package com.example.finalproject_.network.Requests;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DeleteEventRequest extends BasicEventRequest{

    public DeleteEventRequest() {
        super();
    }

    public Call<ResponseBody> delete(String eventId) {
        return eventAPIInterface.deleteEvent(eventId);
    }
}
