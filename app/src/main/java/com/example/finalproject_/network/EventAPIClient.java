package com.example.finalproject_.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventAPIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new AuthInterceptor());

            OkHttpClient client = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.33.24:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit;
    }
}
