package com.example.finalproject_.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject_.adapters.CustomAdapter;
import com.example.finalproject_.models.MeetingModel;
import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton going_add_meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        going_add_meeting = findViewById(R.id.fab);

        System.out.println(MyProperties.getInstance().addNewMeeting);

        if (MyProperties.getInstance().addNewMeeting) {
            MyProperties.getInstance().addNewMeeting = false;
            String meeting_name = getIntent().getStringExtra("meeting_name_str");
            String date = getIntent().getStringExtra("date_str");
            String time = getIntent().getStringExtra("time_str");
            String location = getIntent().getStringExtra("location_str");
            String caregiver_details = getIntent().getStringExtra("caregiver_details_str");

            String kabala = getIntent().getStringExtra("kabala_str");
            String submitted = getIntent().getStringExtra("submitted_str");
            String refund = getIntent().getStringExtra("refund_str");

            MeetingModel new_meetingModel = new MeetingModel(meeting_name, date, time, location, caregiver_details, false, kabala, submitted, refund);
            MyProperties.getInstance().arrayMeeting.addMeeting(new_meetingModel);

        } else if (MyProperties.getInstance().firstTimeOpen) {
            MyProperties.getInstance().firstTimeOpen = false;
            MyProperties.getInstance().arrayMeeting.CreateDataBase();
        } else if (MyProperties.getInstance().changeMeeting) {
            MyProperties.getInstance().changeMeeting = false;
            String meeting_name = getIntent().getStringExtra("meeting_name_str");
            String date = getIntent().getStringExtra("date_str");
            String time = getIntent().getStringExtra("time_str");
            String location = getIntent().getStringExtra("location_str");
            String caregiver_details = getIntent().getStringExtra("caregiver_details_str");

        }


        going_add_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerViewCon);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CustomAdapter customAdapter = new CustomAdapter(MyProperties.getInstance().arrayMeeting.am_to_al());
        recyclerView.setAdapter(customAdapter);
    }


}