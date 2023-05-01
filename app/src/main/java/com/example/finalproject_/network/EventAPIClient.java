package com.example.finalproject_.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventAPIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = HttpClient.getInstance();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.33.19:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit;
    }
}
