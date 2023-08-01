package com.example.finalproject_.interfaces;

import java.io.IOException;

import okhttp3.Response;

public interface NetworkResponseCallback {
    void onSuccess(Response driveFile) throws IOException;
    void onError(Throwable throwable);
}