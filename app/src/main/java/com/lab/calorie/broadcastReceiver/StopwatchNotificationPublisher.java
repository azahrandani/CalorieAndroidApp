package com.lab.calorie.broadcastReceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import com.lab.calorie.R;
import com.lab.calorie.activity.TimerActivity;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class StopwatchNotificationPublisher extends BroadcastReceiver {

    private static final String CHANNEL_ID = "com.lab.calorie.notificationCalorie.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("###Received by stopwatch");
        String exercise = intent.getStringExtra("exercise").toLowerCase();
        int minutes = intent.getIntExtra("minutes", 0);
        String calories = intent.getStringExtra("calories");

        Intent targetIntent = new Intent(context, TimerActivity.class);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(context);

        Resources resources = context.getResources();

        String notificationTitle = resources.getString(R.string.congrats);
        String notificationText = resources.getString(R.string.youve_burned) +
                                    " " + calories + resources.getString(R.string.calories) +
                                    " " + resources.getString(R.string.by_doing) + " " + exercise +
                                    " " + resources.getString(R.string.for_minutes) +
                                    " " + minutes + " " + resources.getString(R.string.minutes);

        Notification notification = builder.setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setTicker("Exercise Alert!")
                .setSmallIcon(R.drawable.icon_calorie)
                .setAutoCancel(true)
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
