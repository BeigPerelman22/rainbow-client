package com.example.finalproject_.network;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import com.example.finalproject_.utils.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Get the user's token from Shared Preferences
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        // Add the token to the request headers
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Proceed with the request and return the response
        return chain.proceed(request);
    }
}

