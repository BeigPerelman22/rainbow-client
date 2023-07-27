package com.example.finalproject_;

//package com.ramps;

public class MyProperties {
    private static MyProperties mInstance = null;
    ///main of the app
    private boolean isDataInit = false;
    //to know which button  open to pdf
    public static boolean btn_kabala_b = false;
    public static boolean btn_Submitted_b = false;
    public static boolean btn_Refund_received_b = false;

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

    public boolean getIsDataInit() {
        return isDataInit;
    }

    public void setIsDataInit(boolean isDataInit) {
        this.isDataInit = isDataInit;
    }
}