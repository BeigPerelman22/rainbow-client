package com.example.finalproject_;

//package com.ramps;

public class MyProperties {
    private static MyProperties mInstance = null;
    ///main of the app
    public boolean firstTimeOpen = true;
    public boolean addNewMeeting = false;
    public boolean changeMeeting = false;
    //to know which button  open to pdf
    public static boolean btn_kabala_b = false;
    public static boolean btn_Submitted_b = false;
    public static boolean btn_Refund_received_b = false;

    public static int ID_NUM = 0;

    public ArrayMeeting arrayMeeting = new ArrayMeeting();

    private EventList eventList;


    protected MyProperties() {
        eventList = new EventList();
    }

    public static synchronized MyProperties getInstance() {
        if (null == mInstance) {
            mInstance = new MyProperties();
        }
        return mInstance;
    }

    public EventList getEventList() {
        return eventList;
    }
}