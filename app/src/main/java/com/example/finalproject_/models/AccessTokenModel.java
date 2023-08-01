package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

public class AccessTokenModel {

    @SerializedName("access_token")
    public String accessToken;


    @SerializedName("error")
    public String error;
}
