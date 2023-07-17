package com.example.finalproject_.network;

import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        String calendarId = SharedPreferencesUtils.getString(MyApplication.getInstance(), "calendar_id", "");
        String token = SharedPreferencesUtils.getString(MyApplication.getInstance(), "token", "");

        Request request = chain.request().newBuilder()
                .addHeader("calendarId", calendarId)
                .addHeader("token", token)
                .build();


        return chain.proceed(request);
    }
}

