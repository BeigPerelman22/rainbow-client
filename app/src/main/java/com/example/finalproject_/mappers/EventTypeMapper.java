package com.example.finalproject_.mappers;

import java.util.HashMap;

public class EventTypeMapper extends HashMap<String, Integer> {

    public EventTypeMapper() {
        this.put("בחר סוג פגישה", 0);
        this.put("רופא", 1);
        this.put("קלינאית תקשורת", 2);
        this.put("טיפול רגשי", 3);
        this.put("ריפוי בעיסוק", 4);
    }

    public Integer getValueForEventType(String eventType) {
        return this.get(eventType);
    }

}
