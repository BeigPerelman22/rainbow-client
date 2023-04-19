package com.example.finalproject_.interfaces;

import com.example.finalproject_.models.AccessTokenModel;
import com.example.finalproject_.models.AuthTokenModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokenAPIInterface {

    @POST("/token")
    Call<AccessTokenModel> getToken(@Body AuthTokenModel authTokenModel);
}

