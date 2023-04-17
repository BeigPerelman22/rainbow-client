package com.example.finalproject_;

public class Meeting {
    String meeting_name;// שם הפגישה
    String date;//תאריך
    String time;//שעה
    String location;//מיקום
    String caregiver_details;//פרטי המטפל
    boolean took_place;//התקיימה
    String receipt;//קבלה
    String submitted;//הוגש לקופת חולים
    String returned;//הוחזר הכסף


    public Meeting() {
        meeting_name = "";// שם הפגישה
        date = "00:00 01/01/2023";//תאריך
        time = "00:00";//שעה
        location = "אין מיקום";//מיקום
        caregiver_details = "";//פרטי המטפל
        took_place = false;//התקיימה
        receipt = null;//קבלה
        submitted = null;//הוגש לקופת חולים
        returned = null;//הוחזר הכסף
    }

    public Meeting(String meeting_name, String date, String time, String location, String caregiver_details, Boolean took_place, String receipt, String submitted, String returned) {
        this.meeting_name = meeting_name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.caregiver_details = caregiver_details;
        this.took_place = took_place;
        this.receipt = receipt;
        this.submitted = submitted;
        this.returned = returned;
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