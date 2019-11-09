package com.lab.calorie;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class NotificationPublisher extends BroadcastReceiver {

    private static final String CHANNEL_ID = "com.lab.calorie.notificationCalorie.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle menuBundle = intent.getBundleExtra("menu");
        Menu menu = (Menu) menuBundle.getSerializable("menu");

        Intent targetIntent = new Intent(context, ListMenuActivity.class);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        targetIntent.putExtra("menu", menuBundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle("Calorie")
                .setContentText("Click here to see what to eat for today!")
                .setTicker("Food Alert!")
                .setSmallIcon(R.drawable.icon_calorie)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationCalorie",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }
}
