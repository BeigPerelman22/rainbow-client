package com.example.finalproject_.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.finalproject_.models.EventModel;
import com.example.finalproject_.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationScheduler {
    private static final int NOTIFICATION_ID = 1001;
    private static final String NOTIFICATION_CHANNEL_ID = "event_channel";

    private Context context;

    public NotificationScheduler(Context context) {
        this.context = context;
    }

    public void scheduleEventNotifications(List<EventModel> events) {
        for (EventModel event : events) {
            scheduleNotification(event);
        }
    }

    public void addNotification(EventModel event) {
        scheduleNotification(event);
    }

    public void updateNotification(EventModel event) {
        cancelNotification(event);
        scheduleNotification(event);
    }

    public void deleteNotification(EventModel event) {
        cancelNotification(event);
    }

    public void cancelAllNotifications() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void scheduleNotification(EventModel event) {
        Date eventTime = DateTimeUtils.dateTimeAsStringToDate(event.getStartTime());
        Date notificationTime = subtractOneHour(eventTime);

        // Schedule the notification
        long notificationTimeInMillis = notificationTime.getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        long delayInMillis = notificationTimeInMillis - currentTimeInMillis;

        if (delayInMillis > 0) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent notificationIntent = new Intent(context, NotificationReceiver.class);
            notificationIntent.putExtra("eventId", generateNotificationId(event));
            notificationIntent.putExtra("eventDescription", event.getDescription());
            PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, generateNotificationId(event), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, currentTimeInMillis + delayInMillis, notificationPendingIntent);
            }
        }
    }

    private void cancelNotification(EventModel event) {
        int notificationId = generateNotificationId(event);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(notificationPendingIntent);
        }
    }

    private Date subtractOneHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        return calendar.getTime();
    }

    private int generateNotificationId(EventModel eventModel) {
        int hashCode = eventModel.getId().hashCode();
        int notificationId = Math.abs(hashCode);
        return notificationId;
    }
}

