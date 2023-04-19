//package com.example.finalproject_.services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//import com.example.finalproject_.R;
//import com.example.finalproject_.interfaces.TokenAPIInterface;
//import com.example.finalproject_.models.AccessTokenModel;
//import com.example.finalproject_.models.AuthTokenModel;
//import com.example.finalproject_.network.TokenApiClient;
//
//
//public class AuthTokenService  {
//
//    private TokenAPIInterface tokenAPIInterface;
//
//    public AuthTokenService() {
//        tokenAPIInterface = TokenApiClient.getClient().create(TokenAPIInterface.class);
//    }
//
//    public String getAccessTokenFromAuthCode(String authCode) {
//        AuthTokenModel authTokenModel = new AuthTokenModel();
//
//        authTokenModel.setAuthCode(authCode);
//        authTokenModel.setClientId();
//
//        tokenAPIInterface.getToken()
//    }
//}
