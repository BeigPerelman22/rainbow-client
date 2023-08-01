package com.example.finalproject_.mappers;

import java.util.HashMap;

public class EventColorMapper extends HashMap<Integer, String> {

    public EventColorMapper() {
        this.put(0, "#EBF3FA");
        this.put(1, "#FAECF0");
        this.put(2, "#DFE3F8");
        this.put(3, "#FFE2F4F4");
        this.put(4, "#DFE3F8");
    }

    public String getColorByColorId(Integer colorId) {
        return this.get(colorId);
    }

}
