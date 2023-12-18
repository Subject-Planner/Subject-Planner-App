package com.demo.subjectplanner.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.demo.subjectplanner.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventName = intent.getStringExtra("eventName");

        // Build and show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "EVENT_CHANNEL")
                .setSmallIcon(R.drawable.white_logo)
                .setContentTitle("Event Reminder")
                .setContentText("Your "+eventName + " is scheduled soon!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }
}

