package com.example.finalproject_;


import java.io.Serializable;

public class TypeMeeting implements Serializable {

    private String name;
    private int img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return img;
    }

    public void setImage(int img) {
        this.img = img;
    }
}