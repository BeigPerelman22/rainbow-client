package com.example.finalproject_.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.activities.ChangeMeetingActivity;
import com.example.finalproject_.models.MeetingModel;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MeetingsViewHolder> {

    public CustomAdapter(ArrayList<MeetingModel> meetingModels) {
        MyProperties.getInstance().arrayMeeting.meetingModelArrayList = meetingModels;
    }

    @NonNull
    @Override
    public MeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View meetingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new MeetingsViewHolder(meetingView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsViewHolder holder, int position) {
        MeetingModel meetingModel = MyProperties.getInstance().arrayMeeting.getMeetingArrayList().get(position);

        holder.nameMeetingVH.setText(meetingModel.getMeeting_name());
        holder.dateMeetingVH.setText(meetingModel.getDate());
        holder.timeMeetingVH.setText(meetingModel.getTime());


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
                intent.putExtra("meeting_name_str", meetingModel.getMeeting_name());
                intent.putExtra("date_str", meetingModel.getDate());
                intent.putExtra("time_str", meetingModel.getTime());
                intent.putExtra("location_str", meetingModel.getLocation());
                intent.putExtra("caregiver_details_str", meetingModel.getCaregiver_details());

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
