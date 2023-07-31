package com.example.finalproject_.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attachments {

    @SerializedName("attachments")
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setSubmittedFile(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
