package com.demo.subjectplanner.activity;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.demo.subjectplanner.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventName = intent.getStringExtra("eventName");


        Intent openAppIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(openAppIntent);

        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        showNotification(context, eventName, pendingIntent);
    }

    private void showNotification(Context context, String eventName, PendingIntent intent) {
        // Build and show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "EVENT_CHANNEL")
                .setSmallIcon(R.drawable.transplogo)
                .setContentTitle("Event Reminder")
                .setContentText("Your " + eventName + " is scheduled soon!")
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }
}
