package com.example.finalproject_.view_holders;

import android.view.View;
import android.widget.Button;
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
import java.util.concurrent.locks.Condition;

public class MeetingViewHolder extends RecyclerView.ViewHolder {
    public TextView nameMeetingVH;
    public TextView dateMeetingVH;
    public TextView timeMeetingVH;
    public CardView cardView;
    Button btn_took_place, btn_kabala, btn_sub, btn_return;

    public MeetingViewHolder(View itemView) {
        super(itemView);

        nameMeetingVH = itemView.findViewById(R.id.meetingName);
        dateMeetingVH = itemView.findViewById(R.id.dateMeeting);
        timeMeetingVH = itemView.findViewById(R.id.time);
        btn_took_place = itemView.findViewById(R.id.button5);
        btn_kabala = itemView.findViewById(R.id.button4);
        btn_sub = itemView.findViewById(R.id.button2);
        btn_return = itemView.findViewById(R.id.button1);
        cardView = itemView.findViewById(R.id.cardv);
    }

    public void bindData(EventModel meeting) {
        nameMeetingVH.setText(meeting.getDescription());
        dateMeetingVH.setText(meeting.getStartTime());
        timeMeetingVH.setText(meeting.getStartTime());
        setButtonBackground(btn_took_place, meeting.isTookPlace());
        setButtonBackground(btn_kabala, meeting.isHasReceipt());
        setButtonBackground(btn_sub, meeting.isSubmitted());
        setButtonBackground(btn_return, meeting.isMoneyRefund());
    }

    private void setButtonBackground(Button button, boolean condition) {
        if (condition) {
            button.setBackgroundResource(R.drawable.round_button_green);
        }
    }
}