package com.example.finalproject_.models;

public class MeetingModel {
    int id;
    String meeting_name;// שם הפגישה
    String date;//תאריך
    String time;//שעה
    String location;//מיקום
    String caregiver_details;//פרטי המטפל
    boolean took_place;//התקיימה


    public boolean getReceipt_bool() {
        return receipt_bool;
    }

    public void setReceipt_bool(boolean receipt_bool) {
        this.receipt_bool = receipt_bool;
    }

    public boolean getSubmitted_bool() {
        return submitted_bool;
    }

    public void setSubmitted_bool(boolean submitted_bool) {
        this.submitted_bool = submitted_bool;
    }

    public boolean getReturned_bool() {
        return returned_bool;
    }

    public void setReturned_bool(boolean returned_bool) {
        this.returned_bool = returned_bool;
    }

    boolean receipt_bool;// bool קבלה
    boolean submitted_bool;// bool  הוגש לקופת חולים
    boolean returned_bool;// bool  הוחזר הכסף

    String receipt;//קבלה
    String submitted;//הוגש לקופת חולים
    String returned;//הוחזר הכסף


    public MeetingModel(int id, String meeting_name, String date, String time, String location, String caregiver_details, String receipt, String submitted, String returned, boolean took_place, boolean receipt_bool, boolean submitted_bool, boolean returned_bool) {
        this.id = id;
        this.meeting_name = meeting_name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.caregiver_details = caregiver_details;

        this.receipt = receipt;
        this.submitted = submitted;
        this.returned = returned;
        this.took_place = took_place;
        this.receipt_bool = receipt_bool;
        this.submitted_bool = submitted_bool;
        this.returned_bool = returned_bool;
    }

    public int getId() {
        return id;
    }

    public String getMeeting_name() {
        return meeting_name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getCaregiver_details() {
        return caregiver_details;
    }

    public boolean getTook_place() {
        return took_place;
    }

    public String getReceipt() {
        return receipt;
    }

    public String getSubmitted() {
        return submitted;
    }

    public String getReturned() {
        return returned;
    }

    public void setId(int id) {
        this.id = this.id;
    }

    public void setMeeting_name(String meeting_name) {
        this.meeting_name = meeting_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCaregiver_details(String caregiver_details) {
        this.caregiver_details = caregiver_details;
    }

    public void setTook_place(boolean took_place) {
        this.took_place = took_place;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }
}