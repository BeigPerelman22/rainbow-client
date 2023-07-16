package com.example.finalproject_;

import com.example.finalproject_.models.EventModel;

import java.util.ArrayList;

public class EventList {

    private ArrayList<EventModel> events;

    public EventList() {
    }

    public void setEvents(ArrayList<EventModel> events) {
        this.events = events;
    }

    public ArrayList<EventModel> getEvents() {
        return events;
    }

    public void deleteEvent(String id) {
        for (EventModel event : events) {
            if (id.equals(event.getId())) {
                events.remove(event);
                break;
            }
        }
    }

    public void updateEvent(EventModel updatedEvent) {
        EventModel eventToUpdate = null;

        for (EventModel currEvent : events) {
            if (currEvent.getId().equals(updatedEvent.getId())) {
                eventToUpdate = currEvent;
                break;
            }
        }

        int index = events.indexOf(eventToUpdate);
        events.set(index, updatedEvent);
    }
}
