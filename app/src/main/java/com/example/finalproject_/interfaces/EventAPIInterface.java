package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.event_requests.CreateEventRequestModel;
import com.example.finalproject_.models.event_requests.DeleteEventRequestModel;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.event_requests.TokenRequestModel;
import com.example.finalproject_.models.event_requests.UpdateEventRequestModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface EventAPIInterface {

    @POST("/calendar/events")
    Call<List<EventModel>> getEvents(@Body TokenRequestModel tokenRequestModel);
    @POST("/calendar/newevent")
    Call<EventModel> createEvent(@Body CreateEventRequestModel createEvent);

    @PUT("/calendar/updateevent")
    Call<EventModel> updateEvent(@Body UpdateEventRequestModel updateEvent);

    @POST("/calendar/deleteevent")
    Call<ResponseBody> deleteEvent(@Body DeleteEventRequestModel deleteEvent);


}
