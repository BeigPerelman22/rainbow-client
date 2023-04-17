package com.example.finalproject_;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ArrayMeeting {
    ArrayList<Meeting> meetingArrayList = new ArrayList<Meeting>();

    public ArrayMeeting(ArrayList<Meeting> meetingArrayList)//constructor
    {
        this.meetingArrayList = meetingArrayList;
    }

    public ArrayMeeting()//default constructor
    {

    }

    public ArrayList<Meeting> getMeetingArrayList()//get arraylist
    {
        return meetingArrayList;
    }

    public void addMeeting(Meeting meeting) {

        meetingArrayList.add(0, meeting);

    }

    public void changeMeeting(Meeting meeting) {

    }

    public void deleteMeeting(Meeting meeting) {


    }

    public ArrayList<Meeting> am_to_al() {

        return meetingArrayList;
    }

    public ArrayList<Meeting> CreateDataBase() {

        Meeting m1 = new Meeting("אבחון קלינאית תקשורת", "01/11/2023", "10:30", "רבי עקיבא 122 בני ברק", "חיה אפשטיין 050:3126578", false, "", "", "");
        Meeting m2 = new Meeting("הדרכת הורים בגן", "03/11/2023", "11:00", "בן גוריון 18 רמת גן", "רות כהן 052:6161743", false, "", "", "");
        Meeting m3 = new Meeting("אבחון נירולוג", "01/12/2023", "09:30", "ביאליק 15 רמת גן", "מוחמד אבו פאני 054:5381649", false, "", "", "");
        Meeting m4 = new Meeting("טיפול ריפוי בעיסוק", "05/12/2023", "09:30", "ז'בוטיסקי 128 בני ברק", "יוסף קירשברג 052:8790168 ", false, "", "", "");
        Meeting m5 = new Meeting("טיפול קלינאית תקשורת", "09/12/2023", "13:30", "רבי עקיבא 122 בני ברק", "חיה אפשטיין 050:3126578", false, "", "", "");
        Meeting m6 = new Meeting("הדרכת הורים בגן", "22/12/2023", "10:30", "בן גוריון 18 רמת גן", "רות כהן 052:6161743", false, "", "", "");

        //ArrayList<Meeting> meetingArrayList=new ArrayList<Meeting>();

        meetingArrayList.add(m1);
        meetingArrayList.add(m2);
        meetingArrayList.add(m3);
        meetingArrayList.add(m4);
        meetingArrayList.add(m5);
        meetingArrayList.add(m6);


        return meetingArrayList;
    }
}
