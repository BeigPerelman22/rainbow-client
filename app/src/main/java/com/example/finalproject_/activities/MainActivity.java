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
        call.enqueue(getEventsCallBack());

        going_add_meeting = findViewById(R.id.fab);

        going_add_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

    }

    private Callback<List<EventModel>> getEventsCallBack() {
        return new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (!Objects.isNull(response.body())) {
                    events = new ArrayList<>(response.body());
                    MyProperties.getInstance().getEventList().setEvents(events);
                    recyclerView = findViewById(R.id.recyclerViewCon);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    CustomAdapter customAdapter = new CustomAdapter(MyProperties.getInstance().getEventList().getEventList());
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
        };
    }
}