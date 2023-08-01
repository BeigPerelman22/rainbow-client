package com.example.finalproject_.utils;

import android.net.Uri;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DriveRequestUtils {

    public DriveRequestUtils() {
    }

    public OkHttpClient createClient() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        return client;
    }

    public Request createRequest(Uri uri) {
        String token = SharedPreferencesUtils.getString(MyApplication.getInstance(), "token", "");
        File file = FileUtil.copyFile(MyApplication.getInstance(), uri);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "",
                        RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();

        Request request = new Request.Builder()
                .url("https://www.googleapis.com/upload/drive/v3/files?fields=webViewLink,mimeType,name,id")
                .method("POST", body)
                .addHeader("Content-Disposition", "attachment; filename=\"my_custom_filename.txt\"")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return request;
    }

}
