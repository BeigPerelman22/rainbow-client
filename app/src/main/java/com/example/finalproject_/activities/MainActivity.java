package com.example.finalproject_.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.finalproject_.adapters.CustomAdapter;
import com.example.finalproject_.interfaces.EventAPIInterface;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.MeetingModel;
import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.models.TokenRequestModel;
import com.example.finalproject_.network.EventAPIClient;
import com.example.finalproject_.utils.LoaderUtils;
import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton going_add_meeting;
    private ArrayList<EventModel> events = new ArrayList<>();

    private EventAPIInterface eventAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#94C8F0"));

        eventAPIInterface = EventAPIClient.getClient().create(EventAPIInterface.class);

        String calendarId = SharedPreferencesUtils.getString(this, "calendar_id", "");
        String token = SharedPreferencesUtils.getString(this, "token", "");

        TokenRequestModel tokenRequestModel = new TokenRequestModel();

        tokenRequestModel.setToken(token);
        tokenRequestModel.setCalendarId(calendarId);

        Call<List<EventModel>> call = eventAPIInterface.getEvents(tokenRequestModel);
        call.enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (!Objects.isNull(response.body())) {
                    events = new ArrayList<>(response.body());
                    recyclerView = findViewById(R.id.recyclerViewCon);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    CustomAdapter customAdapter = new CustomAdapter(events);
                    recyclerView.setAdapter(customAdapter);
                    customAdapter.sortItemsByDate(false);
                    LoaderUtils.hideLoader();
                } else {
                    Toast.makeText(MyApplication.getInstance(), "Fetch event failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Toast.makeText(MyApplication.getInstance(), "Fetch event failed", Toast.LENGTH_SHORT).show();
            }
        });

        going_add_meeting = findViewById(R.id.fab);

        if (MyProperties.getInstance().addNewMeeting) {
            MyProperties.getInstance().addNewMeeting = false;
            addNewMeeting();
        }
        if (MyProperties.getInstance().firstTimeOpen) {
            MyProperties.getInstance().firstTimeOpen = false;
            MyProperties.getInstance().arrayMeeting.CreateDataBase();
        }
        if (MyProperties.getInstance().changeMeeting) {
            MyProperties.getInstance().changeMeeting = false;
            updateMeeting();
        }

        going_add_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateMeeting() {
        int ID = getIntent().getIntExtra("CH->MA", 99);
        String meeting_name = getIntent().getStringExtra("meeting_name_str");
        String date = getIntent().getStringExtra("date_str");
        String time = getIntent().getStringExtra("time_str");
        String location = getIntent().getStringExtra("location_str");
        String caregiver_details = getIntent().getStringExtra("caregiver_details_str");


        String kabala = getIntent().getStringExtra("kabala_f");
        String submitted = getIntent().getStringExtra("submitted_f");
        String refund = getIntent().getStringExtra("refund_f");


        boolean took_place = getIntent().getBooleanExtra("took_placeBOOL", false);
        boolean kabala_bool = getIntent().getBooleanExtra("kabalaBOOL", false);
        boolean submitted_bool = getIntent().getBooleanExtra("submittedBOOL", false);
        boolean refund_bool = getIntent().getBooleanExtra("refund_receivedBOOL", false);


        MyProperties.getInstance().arrayMeeting.changeMeeting(ID, meeting_name, date, time, location, caregiver_details, kabala, submitted, refund, took_place, kabala_bool, submitted_bool, refund_bool);
    }

    private void addNewMeeting() {
        String meeting_name = getIntent().getStringExtra("meeting_name_str");
        String date = getIntent().getStringExtra("date_str");
        String time = getIntent().getStringExtra("time_str");
        String location = getIntent().getStringExtra("location_str");
        String caregiver_details = getIntent().getStringExtra("caregiver_details_str");

        String kabala = getIntent().getStringExtra("kabala_f");
        String submitted = getIntent().getStringExtra("submitted_f");
        String refund = getIntent().getStringExtra("refund_f");


        boolean took_place = getIntent().getBooleanExtra("took_placeBOOL", false);
        boolean kabala_bool = getIntent().getBooleanExtra("kabalaBOOL", false);
        boolean submitted_bool = getIntent().getBooleanExtra("submittedBOOL", false);
        boolean refund_bool = getIntent().getBooleanExtra("refund_receivedBOOL", false);

        MeetingModel new_meeting = new MeetingModel(0, meeting_name, date, time, location, caregiver_details, kabala, submitted, refund, took_place, kabala_bool, submitted_bool, refund_bool);
        MyProperties.getInstance().arrayMeeting.addMeeting(new_meeting);
    }
}