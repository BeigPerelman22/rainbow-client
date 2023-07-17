package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.event_requests.CreateEventRequestModel;
import com.example.finalproject_.models.event_requests.UpdateEventRequestModel;

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

    @GET("/calendar/events")
    Call<List<EventModel>> getEvents();

    @POST("/calendar/newevent")
    Call<EventModel> createEvent(@Body CreateEventRequestModel createEvent);

    @PUT("/calendar/updateevent")
    Call<EventModel> updateEvent(@Body UpdateEventRequestModel updateEvent);

    @DELETE("/calendar/deleteevent")
    Call<ResponseBody> deleteEvent(@Query("id") String id);


}
