package com.example.finalproject_.activities;

import static com.example.finalproject_.constants.RequestCode.NEW_EVENT_REQUEST_CODE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_.MyProperties;
import com.example.finalproject_.R;
import com.example.finalproject_.adapters.CustomAdapter;
import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.network.EventRequestsExecutor;
import com.example.finalproject_.notifications.NotificationScheduler;
import com.example.finalproject_.utils.MyApplication;
import com.example.finalproject_.utils.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private View emptyStateLayout;
    private FloatingActionButton going_add_meeting;
    private ImageView about_btn;
    private final ArrayList<EventModel> events = new ArrayList<>();
    private NotificationScheduler notificationScheduler;
    private EventRequestsExecutor eventRequestsExecutor;
    private CustomAdapter customAdapter;
    private ConstraintLayout loaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLoader();
        setActionBar();
        initializeViews();
        createNotificationPermission();
        setStatusBarColor();
        setupEventExecutor();
        fetchEvents();
        setClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            customAdapter.sortItemsByDate(false);
            updateVisibility();
        }
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewCon);
        emptyStateLayout = findViewById(R.id.emptyStateLayout);
        going_add_meeting = findViewById(R.id.fab);
        about_btn = findViewById(R.id.about_btn);
    }

    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FF018786"));
    }

    private void setupEventExecutor() {
        eventRequestsExecutor = new EventRequestsExecutor();
    }

    private void fetchEvents() {
        if (!MyProperties.getInstance().getIsDataInit()) {
            Call<List<EventModel>> call = eventRequestsExecutor.fetchEvents();
            call.enqueue(getEventsCallback());
        } else {
            setupRecyclerView();
        }
    }

    private Callback<List<EventModel>> getEventsCallback() {
        return new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (response.isSuccessful()) {
                    List<EventModel> eventList = response.body();
                    if (eventList != null) {
                        events.addAll(eventList);
                        MyProperties.getInstance().getEventList().setEvents(events);
                        MyProperties.getInstance().setIsDataInit(true);
                        setupEventNotification(events);
                        setupRecyclerView();
                    }
                    updateVisibility();
                } else {
                    ToastUtils.showShortToast("Fetch event failed");
                }
                removeLoader();
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                ToastUtils.showShortToast("Fetch event failed");
            }
        };
    }

    private void setupEventNotification(List<EventModel> events) {
        notificationScheduler = new NotificationScheduler(MyApplication.getInstance());
        notificationScheduler.scheduleEventNotifications(events);
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        customAdapter = new CustomAdapter(this, MyProperties.getInstance().getEventList().getEvents());
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
                        openDeleteDialog(position);
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
    }

    private void setClickListener() {
        going_add_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddMeeting();
            }
        });
        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Information.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToAddMeeting() {
        Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
        startActivityForResult(intent, NEW_EVENT_REQUEST_CODE);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void openDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("אתה בטוח שאתה מעוניין למחוק את הפגישה")
                .setCancelable(false)
                .setPositiveButton("אישור", (DialogInterface dialog, int id) -> deleteEvent(position))
                .setNegativeButton("ביטול", (DialogInterface dialog, int id) -> cancelDelete(position));
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteEvent(int position) {
        EventModel event = MyProperties.getInstance().getEventList().getEvents().get(position);

        Call<ResponseBody> call = eventRequestsExecutor.deleteEvent(event.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    notificationScheduler.deleteNotification(event);
                    MyProperties.getInstance().getEventList().deleteEvent(event.getId());
                    recyclerView.getAdapter().notifyItemRemoved(position);
                    updateVisibility();
                    ToastUtils.showShortToast("Delete event successful");
                } else {
                    ToastUtils.showShortToast("Delete event failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShortToast("Delete event failed");
            }
        });
    }

    private void cancelDelete(int position) {
        recyclerView.getAdapter().notifyItemChanged(position);
    }

    private void createNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String des = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("event_channel", name, importance);
            channel.setDescription(des);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setActionBar() {
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customActionBarView = inflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(customActionBarView);
    }

    private void updateVisibility() {
        if (MyProperties.getInstance().getEventList().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }

    private void setLoader() {
        LayoutInflater inflater = getLayoutInflater();
        loaderLayout = (ConstraintLayout) inflater.inflate(R.layout.icon_loader, null);
        addContentView(loaderLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    private void removeLoader() {
        if (loaderLayout != null) {

            ViewGroup parent = (ViewGroup) loaderLayout.getParent();
            if (parent != null) {
                parent.removeView(loaderLayout);
            }
        }
    }
}