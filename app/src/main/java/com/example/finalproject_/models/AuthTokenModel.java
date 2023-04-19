package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

public class AuthTokenModel {

    @SerializedName("client_id")
    private String clientId;


    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("code")
    private String authCode;

    @SerializedName("grant_type")
    private String grantType;

    public AuthTokenModel() {

    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
