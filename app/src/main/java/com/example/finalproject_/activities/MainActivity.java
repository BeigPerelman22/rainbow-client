package com.example.finalproject_.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject_.adapters.CustomAdapter;
import com.example.finalproject_.interfaces.EventAPIInterface;
import com.example.finalproject_.models.EventsResponseModel;
import com.example.finalproject_.models.MeetingModel;
import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.models.TokenRequestModel;
import com.example.finalproject_.network.EventAPIClient;
import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton going_add_meeting;

    private EventAPIInterface eventAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventAPIInterface = EventAPIClient.getClient().create(EventAPIInterface.class);

        String calendarId = SharedPreferencesUtils.getString(this, "calendar_id", "");
        String token = SharedPreferencesUtils.getString(this, "token", "");

        TokenRequestModel tokenRequestModel = new TokenRequestModel();

        tokenRequestModel.setToken(token);
        tokenRequestModel.setCalendarId(calendarId);

        Call<EventsResponseModel> call = eventAPIInterface.getEvents(tokenRequestModel);
        call.enqueue(new Callback<EventsResponseModel>() {
            @Override
            public void onResponse(Call<EventsResponseModel> call, Response<EventsResponseModel> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<EventsResponseModel> call, Throwable t) {
                Toast.makeText(MyApplication.getInstance(), "Fetch event failed", Toast.LENGTH_SHORT).show();
            }
        });

//        going_add_meeting = findViewById(R.id.fab);
//
//        if (MyProperties.getInstance().addNewMeeting) {
//            MyProperties.getInstance().addNewMeeting = false;
//            String meeting_name = getIntent().getStringExtra("meeting_name_str");
//            String date = getIntent().getStringExtra("date_str");
//            String time = getIntent().getStringExtra("time_str");
//            String location = getIntent().getStringExtra("location_str");
//            String caregiver_details = getIntent().getStringExtra("caregiver_details_str");
//
//            String kabala = getIntent().getStringExtra("kabala_str");
//            String submitted = getIntent().getStringExtra("submitted_str");
//            String refund = getIntent().getStringExtra("refund_str");
//
//            MeetingModel new_meetingModel = new MeetingModel(meeting_name, date, time, location, caregiver_details, false, kabala, submitted, refund);
//            MyProperties.getInstance().arrayMeeting.addMeeting(new_meetingModel);
//
//        } else if (MyProperties.getInstance().firstTimeOpen) {
//            MyProperties.getInstance().firstTimeOpen = false;
//            MyProperties.getInstance().arrayMeeting.CreateDataBase();
//        } else if (MyProperties.getInstance().changeMeeting) {
//            MyProperties.getInstance().changeMeeting = false;
//            String meeting_name = getIntent().getStringExtra("meeting_name_str");
//            String date = getIntent().getStringExtra("date_str");
//            String time = getIntent().getStringExtra("time_str");
//            String location = getIntent().getStringExtra("location_str");
//            String caregiver_details = getIntent().getStringExtra("caregiver_details_str");
//
//        }
//
//
//        going_add_meeting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        recyclerView = findViewById(R.id.recyclerViewCon);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        CustomAdapter customAdapter = new CustomAdapter(MyProperties.getInstance().arrayMeeting.am_to_al());
//        recyclerView.setAdapter(customAdapter);
    }


}