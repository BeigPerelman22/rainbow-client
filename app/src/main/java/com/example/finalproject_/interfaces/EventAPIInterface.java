package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.CreateEventRequestModel;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.TokenRequestModel;
import com.example.finalproject_.models.UpdateEventRequestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface EventAPIInterface {

    @POST("/calendar/events")
    Call<List<EventModel>> getEvents(@Body TokenRequestModel tokenRequestModel);

    @PUT("/calendar/updateevent")
    Call<EventModel> updateEvent(@Body UpdateEventRequestModel updateEvent);

    @POST("/calendar/newevent")
    Call<EventModel> createEvent(@Body CreateEventRequestModel createEvent);

}
