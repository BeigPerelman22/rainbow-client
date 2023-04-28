package com.example.finalproject_;

import com.example.finalproject_.models.MeetingModel;

import java.util.ArrayList;

public class ArrayMeeting {
    ArrayList<MeetingModel> meetingArrayList = new ArrayList<MeetingModel>();

    public ArrayMeeting(ArrayList<MeetingModel> meetingArrayList)//constructor
    {
        this.meetingArrayList = meetingArrayList;
    }

    public int size() {

        return meetingArrayList.size();

    }

    public ArrayMeeting()//default constructor
    {

    }

    public ArrayList<MeetingModel> getMeetingArrayList()//get arraylist
    {
        return meetingArrayList;
    }

    public void addMeeting(MeetingModel meeting) {
        int i = 0;
        while (i != MyProperties.getInstance().arrayMeeting.meetingArrayList.size()) {
            MyProperties.getInstance().arrayMeeting.meetingArrayList.get(i).setId(i + 1);
            i++;

        }

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(0, meeting);


    }

    public void deleteMeeting(int index) {
        getMeetingArrayList().remove(index);

        while (index < MyProperties.getInstance().arrayMeeting.meetingArrayList.size()) {
            MyProperties.getInstance().arrayMeeting.meetingArrayList.get(index).setId(index);
            System.out.println(MyProperties.getInstance().arrayMeeting.meetingArrayList.get(index).getMeeting_name() + " : " + index);
            index++;

        }

    }

    public void changeMeeting(int id, String meeting_name, String date, String time, String location, String caregiver_details, String receipt, String submitted, String returned, boolean took_place, boolean receipt_bool, boolean submitted_bool, boolean returned_bool) {
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setMeeting_name(meeting_name);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setDate(date);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setTime(time);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setLocation(location);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setCaregiver_details(caregiver_details);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setReceipt(receipt);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setSubmitted(submitted);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setReturned(returned);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setTook_place(took_place);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setReceipt_bool(receipt_bool);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setSubmitted_bool(submitted_bool);
        MyProperties.getInstance().arrayMeeting.meetingArrayList.get(id).setReceipt_bool(returned_bool);
    }


    public ArrayList<MeetingModel> am_to_al() {
        return meetingArrayList;
    }

    public ArrayList<MeetingModel> CreateDataBase() {

        MeetingModel m1 = new MeetingModel(0, "אבחון קלינאית תקשורת", "01/11/2023", "10:30", "רבי עקיבא 122 בני ברק", "חיה אפשטיין 050:3126578", "", "", "", true, true, false, false);
        MeetingModel m2 = new MeetingModel(1, "הדרכת הורים בגן", "03/11/2023", "11:00", "בן גוריון 18 רמת גן", "רות כהן 052:6161743", "", "", "", false, false, false, false);
        MeetingModel m3 = new MeetingModel(2, "אבחון נירולוג", "01/12/2023", "09:30", "ביאליק 15 רמת גן", "מוחמד אבו פאני 054:5381649", "", "", "", true, true, true, false);
        MeetingModel m4 = new MeetingModel(3, "טיפול ריפוי בעיסוק", "05/12/2023", "09:30", "ז'בוטיסקי 128 בני ברק", "יוסף קירשברג 052:8790168 ", "", "", "", false, false, false, false);
        MeetingModel m5 = new MeetingModel(4, "טיפול קלינאית תקשורת", "09/12/2023", "13:30", "רבי עקיבא 122 בני ברק", "חיה אפשטיין 050:3126578", "", "", "", true, false, false, false);
        MeetingModel m6 = new MeetingModel(5, "הדרכת הורים בגן", "22/12/2023", "10:30", "בן גוריון 18 רמת גן", "רות כהן 052:6161743", "", "", "", false, false, false, false);
        MeetingModel m7 = new MeetingModel(6, "אבחון נירולוג", "01/12/2023", "10:30", "ביאליק 15 רמת גן", "מוחמד אבו פאני 054:5381649", "", "", "", true, true, true, false);


        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m1);

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m2);

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m3);

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m4);

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m5);

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m6);

        MyProperties.getInstance().arrayMeeting.meetingArrayList.add(m7);

        return MyProperties.getInstance().arrayMeeting.meetingArrayList;
    }
}
