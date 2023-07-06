package com.example.finalproject_.models.event_requests;


import com.google.gson.annotations.SerializedName;

public class UpdateEventRequestModel extends TokenRequestModel {

    @SerializedName("id")
    private String id;

    @SerializedName("summary")
    private String description;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("location")
    private String location;

    @SerializedName("caregiverDetails")
    private String caregiverDetails;

    @SerializedName("hasReceipt")
    private boolean hasReceipt;

    @SerializedName("isTookPlace")
    private boolean isTookPlace;

    @SerializedName("isSubmitted")
    private boolean isSubmitted;

    @SerializedName("isMoneyRefund")
    private boolean isMoneyRefund;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCaregiverDetails() {
        return caregiverDetails;
    }

    public void setCaregiverDetails(String caregiverDetails) {
        this.caregiverDetails = caregiverDetails;
    }

    public boolean isHasReceipt() {
        return hasReceipt;
    }

    public void setHasReceipt(boolean hasReceipt) {
        this.hasReceipt = hasReceipt;
    }

    public boolean isTookPlace() {
        return isTookPlace;
    }

    public void setTookPlace(boolean tookPlace) {
        isTookPlace = tookPlace;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public boolean isMoneyRefund() {
        return isMoneyRefund;
    }

    public void setMoneyRefund(boolean moneyRefund) {
        isMoneyRefund = moneyRefund;
    }
}
