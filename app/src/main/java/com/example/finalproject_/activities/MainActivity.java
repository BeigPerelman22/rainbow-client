package com.example.finalproject_.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.adapters.CustomAdapter;
import com.example.finalproject_.interfaces.EventAPIInterface;
import com.example.finalproject_.models.DeleteEventRequestModel;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.models.TokenRequestModel;
import com.example.finalproject_.network.EventAPIClient;
import com.example.finalproject_.utils.LoaderUtils;
import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import okhttp3.ResponseBody;
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

    public void onBackPressed() {
        moveTaskToBack(true);
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

                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            int position = viewHolder.getAdapterPosition();

                            switch (direction) {
                                case ItemTouchHelper.LEFT:
                                    // Set the item background color to red
                                    showMessage(position);
                                    break;

                                case ItemTouchHelper.RIGHT:
                                    break;

                            }

                        }

                        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                    .addSwipeLeftBackgroundColor(Color.parseColor("#FFB50404"))
                                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)

                                    .create()
                                    .decorate();


                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        }


                    };
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);

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

    private void showMessage(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(" אתה בטוח שאתה מעוניין למחוק את הפגישה ")
                .setCancelable(false)
                .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventModel event = MyProperties.getInstance().getEventList().getEventList().get(position);
                        String calendarId = SharedPreferencesUtils.getString(getApplicationContext(), "calendar_id", "");
                        String token = SharedPreferencesUtils.getString(getApplicationContext(), "token", "");

                        DeleteEventRequestModel deleteEventRequestModel = new DeleteEventRequestModel();

                        deleteEventRequestModel.setId(event.getId());
                        deleteEventRequestModel.setCalendarId(calendarId);
                        deleteEventRequestModel.setToken(token);
                        Call<ResponseBody> call = eventAPIInterface.deleteEvent(deleteEventRequestModel);
                        call.enqueue(deleteEventCallBack());

                        MyProperties.getInstance().getEventList().deleteEvent(event.getId());
                        recyclerView.getAdapter().notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // כאן יש להוסיף קוד שיתבצע כאשר המשתמש לוחץ על כפתור הביטול

                        recyclerView.getAdapter().notifyItemChanged(position);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Callback<ResponseBody> deleteEventCallBack() {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MyApplication.getInstance(), "Delete event failed", Toast.LENGTH_SHORT).show();
            }
        };
    }
}