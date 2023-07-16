package com.example.finalproject_.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.finalproject_.R;
import com.example.finalproject_.activities.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String NOTIFICATION_CHANNEL_ID = "event_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        int eventId = intent.getIntExtra("eventId", -1);
        String eventDesc = intent.getStringExtra("eventDescription");

        // Create an intent to launch the app when the notification is clicked
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.rainbow)
                .setContentTitle("היי מזכירים שיש פגישה עוד שעתיים")
                .setContentText(eventDesc)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(eventId, builder.build());
    }
}
