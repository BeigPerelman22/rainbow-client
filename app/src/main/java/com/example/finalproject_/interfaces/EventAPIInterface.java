package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.TokenRequestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EventAPIInterface {

    @POST("/calendar/events")
    Call<List<EventModel>> getEvents(@Body TokenRequestModel tokenRequestModel);

}
