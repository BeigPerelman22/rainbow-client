package com.example.finalproject_.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.activities.ChangeMeetingActivity;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.MeetingModel;
import com.example.finalproject_.view_holders.MeetingViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomAdapter extends RecyclerView.Adapter<MeetingViewHolder> {

    private ArrayList<EventModel> events;

    public CustomAdapter(ArrayList<EventModel> modelArrayList) {
        events = modelArrayList;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View meetingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new MeetingViewHolder(meetingView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        EventModel currEvent = events.get(position);

        holder.bindData(currEvent);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeMeetingActivity.class);
//                intent.putExtra("meeting_name_str", currEvent.getMeeting_name());
//                intent.putExtra("date_str", currEvent.getDate());
//                intent.putExtra("time_str", currEvent.getTime());
//                intent.putExtra("location_str", currEvent.getLocation());
//                intent.putExtra("caregiver_details_str", currEvent.getCaregiver_details());

                view.getContext().startActivity(intent);
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
                    return item1.getStartDate().getDateTime().compareTo(item2.getStartDate().getDateTime());
                } else {
                    return item2.getStartDate().getDateTime().compareTo(item1.getStartDate().getDateTime());
                }
            }
        };

        // Sort the list using the comparator
        Collections.sort(events, dateComparator);

        // Notify the adapter that the data has changed after sorting
        notifyDataSetChanged();
    }
}
