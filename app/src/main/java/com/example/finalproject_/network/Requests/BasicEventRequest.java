package com.example.finalproject_.network.Requests;

import com.example.finalproject_.interfaces.EventAPIInterface;
import com.example.finalproject_.network.EventAPIClient;

public abstract class BasicEventRequest {

    protected EventAPIInterface eventAPIInterface;

    public BasicEventRequest() {
        eventAPIInterface = EventAPIClient.getClient().create(EventAPIInterface.class);
    }

}
