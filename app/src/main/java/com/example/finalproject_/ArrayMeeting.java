package com.example.finalproject_;


import com.example.finalproject_.models.MeetingModel;

import java.util.ArrayList;

public class ArrayMeeting {
    public ArrayList<MeetingModel> meetingModelArrayList = new ArrayList<MeetingModel>();

    public ArrayMeeting(ArrayList<MeetingModel> meetingModelArrayList)//constructor
    {
        this.meetingModelArrayList = meetingModelArrayList;
    }

    public ArrayMeeting()//default constructor
    {

    }

    public ArrayList<MeetingModel> getMeetingArrayList()//get arraylist
    {
        return meetingModelArrayList;
    }

    public void addMeeting(MeetingModel meetingModel) {

        meetingModelArrayList.add(0, meetingModel);

    }

    public void changeMeeting(MeetingModel meetingModel) {

    }

    public void deleteMeeting(MeetingModel meetingModel) {


    }

    public ArrayList<MeetingModel> am_to_al() {

        return meetingModelArrayList;
    }

    public ArrayList<MeetingModel> CreateDataBase() {

        MeetingModel m1 = new MeetingModel("אבחון קלינאית תקשורת", "01/11/2023", "10:30", "רבי עקיבא 122 בני ברק", "חיה אפשטיין 050:3126578", false, "", "", "");
        MeetingModel m2 = new MeetingModel("הדרכת הורים בגן", "03/11/2023", "11:00", "בן גוריון 18 רמת גן", "רות כהן 052:6161743", false, "", "", "");
        MeetingModel m3 = new MeetingModel("אבחון נירולוג", "01/12/2023", "09:30", "ביאליק 15 רמת גן", "מוחמד אבו פאני 054:5381649", false, "", "", "");
        MeetingModel m4 = new MeetingModel("טיפול ריפוי בעיסוק", "05/12/2023", "09:30", "ז'בוטיסקי 128 בני ברק", "יוסף קירשברג 052:8790168 ", false, "", "", "");
        MeetingModel m5 = new MeetingModel("טיפול קלינאית תקשורת", "09/12/2023", "13:30", "רבי עקיבא 122 בני ברק", "חיה אפשטיין 050:3126578", false, "", "", "");
        MeetingModel m6 = new MeetingModel("הדרכת הורים בגן", "22/12/2023", "10:30", "בן גוריון 18 רמת גן", "רות כהן 052:6161743", false, "", "", "");

        //ArrayList<Meeting> meetingArrayList=new ArrayList<Meeting>();

        meetingModelArrayList.add(m1);
        meetingModelArrayList.add(m2);
        meetingModelArrayList.add(m3);
        meetingModelArrayList.add(m4);
        meetingModelArrayList.add(m5);
        meetingModelArrayList.add(m6);


        return meetingModelArrayList;
    }
}
