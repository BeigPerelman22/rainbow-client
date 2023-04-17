package com.example.finalproject_;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MeetingsViewHolder> {

    public CustomAdapter(ArrayList<Meeting> meetings) {
        MyProperties.getInstance().arrayMeeting.meetingArrayList = meetings;
    }

    @NonNull
    @Override
    public MeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View meetingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new MeetingsViewHolder(meetingView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsViewHolder holder, int position) {
        Meeting meeting = MyProperties.getInstance().arrayMeeting.getMeetingArrayList().get(position);

        holder.nameMeetingVH.setText(meeting.getMeeting_name());
        holder.dateMeetingVH.setText(meeting.getDate());
        holder.timeMeetingVH.setText(meeting.getTime());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent =new Intent(AddMeetingActivity.this,MainActivity.class);
//
//                intent.putExtra("meeting_name_str",meeting_name_str);
//                intent.putExtra("date_str",date_str);
//                intent.putExtra("time_str",time_str);
//                intent.putExtra("location_str",location_str);
//                intent.putExtra("caregiver_details_str",caregiver_details_str);
                Intent intent = new Intent(view.getContext(), ChangeMeetingActivity.class);
                intent.putExtra("meeting_name_str", meeting.getMeeting_name());
                intent.putExtra("date_str", meeting.getDate());
                intent.putExtra("time_str", meeting.getTime());
                intent.putExtra("location_str", meeting.getLocation());
                intent.putExtra("caregiver_details_str", meeting.getCaregiver_details());

                view.getContext().startActivity(intent);
                //Toast.makeText(view.getContext(),"good",Toast.LENGTH_LONG ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MyProperties.getInstance().arrayMeeting.getMeetingArrayList().size();
    }


    public static class MeetingsViewHolder extends RecyclerView.ViewHolder {
        public TextView nameMeetingVH;
        public TextView dateMeetingVH;
        public TextView timeMeetingVH;
        public CardView cardView;

        public MeetingsViewHolder(@NonNull View itemView) {
            super(itemView);

            nameMeetingVH = itemView.findViewById(R.id.meetingName);
            dateMeetingVH = itemView.findViewById(R.id.dateMeeting);
            timeMeetingVH = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardv);
        }
    }
}
