package com.example.finalproject_.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.R;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.MeetingModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MeetingViewHolder extends RecyclerView.ViewHolder {
    public TextView nameMeetingVH;
    public TextView dateMeetingVH;
    public TextView timeMeetingVH;
    public CardView cardView;

    public MeetingViewHolder(View itemView) {
        super(itemView);

        nameMeetingVH = itemView.findViewById(R.id.meetingName);
        dateMeetingVH = itemView.findViewById(R.id.dateMeeting);
//        timeMeetingVH = itemView.findViewById(R.id.time);
        cardView = itemView.findViewById(R.id.cardv);
    }

    public void bindData(EventModel meeting) {
        nameMeetingVH.setText(meeting.getDescription());
        dateMeetingVH.setText(meeting.getStartDate().getDateTime());
    }
}
