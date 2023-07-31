package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.EventModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EventAPIInterface {

    @GET("/app/calendar/events")
    Call<List<EventModel>> getEvents();

    @POST("/app/calendar/newevent")
    Call<EventModel> createEvent(@Body EventModel createEvent);

    @PUT("/app/calendar/updateevent")
    Call<EventModel> updateEvent(@Body EventModel updateEvent);

    @DELETE("/app/calendar/deleteevent")
    Call<ResponseBody> deleteEvent(@Query("id") String id);


}
