package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.EventsResponseModel;
import com.example.finalproject_.models.TokenRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EventAPIInterface {

    @POST("/calendar/events")
    Call<EventsResponseModel> getEvents(@Body TokenRequestModel tokenRequestModel);

}
