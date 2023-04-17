package com.example.finalproject_.network;

import okhttp3.OkHttpClient;

public class HttpClient {
    private static OkHttpClient client;

    public static synchronized OkHttpClient getInstance() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor())
                    .build();
        }
        return client;
    }
}

