package com.example.finalproject_.adapters;


import static com.example.finalproject_.constants.RequestCode.EDIT_EVENT_REQUEST_CODE;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.R;
import com.example.finalproject_.activities.ChangeMeetingActivity;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.view_holders.MeetingViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomAdapter extends RecyclerView.Adapter<MeetingViewHolder> {
    private ArrayList<EventModel> events;
    private Activity parentActivity;

    public CustomAdapter(Activity activity, ArrayList<EventModel> modelArrayList) {
        events = modelArrayList;
        parentActivity = activity;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View meetingView = LayoutInflater.from(parentActivity).inflate(R.layout.card_view, parent, false);
        return new MeetingViewHolder(meetingView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        EventModel currEvent = events.get(position);
        int itemPosition = position;

        holder.bindData(currEvent);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentActivity, ChangeMeetingActivity.class);
                intent.putExtra("event", currEvent);
                intent.putExtra("itemPosition", itemPosition);
                parentActivity.startActivityForResult(intent,  EDIT_EVENT_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void sortItemsByDate(boolean ascending) {
        // Create a comparator to compare the dates in the list
        Comparator<EventModel> dateComparator = new Comparator<EventModel>() {
            @Override
            public int compare(EventModel item1, EventModel item2) {
                if (ascending) {
                    return item1.getStartTime().compareTo(item2.getStartTime());
                } else {
                    return item2.getStartTime().compareTo(item1.getStartTime());
                }
            }
        };

        // Sort the list using the comparator
        Collections.sort(events, dateComparator);

        // Notify the adapter that the data has changed after sorting
        notifyDataSetChanged();
    }
}
