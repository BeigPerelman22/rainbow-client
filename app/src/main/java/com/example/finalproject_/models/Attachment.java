package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

public class Attachment {

    @SerializedName("iconLink")
    private String iconLink;

    @SerializedName("fileUrl")
    private String fileUrl;

    @SerializedName("title")
    private String title;


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
