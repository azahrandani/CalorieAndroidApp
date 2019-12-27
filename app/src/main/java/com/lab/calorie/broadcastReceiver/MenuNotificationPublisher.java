package com.lab.calorie.broadcastReceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.lab.calorie.R;
import com.lab.calorie.activity.ListMenuActivity;
import com.lab.calorie.model.Food;
import com.lab.calorie.model.Menu;

import java.util.ArrayList;
import java.util.List;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MenuNotificationPublisher extends BroadcastReceiver {

    private static final String CHANNEL_ID = "com.lab.calorie.notificationCalorie.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle menuBundle = intent.getBundleExtra("menu");
        Menu menu = (Menu) menuBundle.getSerializable("menu");

        Bundle foodListBundle = intent.getBundleExtra("food_list");
        List<Food> selectedFoodList = new ArrayList<>();

        for (String key : foodListBundle.keySet()) {
            selectedFoodList.add((Food) foodListBundle.getSerializable(key));
        }

        Intent targetIntent = new Intent(context, ListMenuActivity.class);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        targetIntent.putExtra("menu", menuBundle);
        System.out.println("###id dari menu yg disimpen di MenuNotificationPublisher " + menu.getId());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle("Calorie: Your food for today")
                .setContentText(generateStringFoodList(selectedFoodList))
                .setTicker("Food Alert!")
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

    private String generateStringFoodList(List<Food> selectedFoodList) {
        String result = "";
        for (Food food : selectedFoodList) {
            result += food.getName() + "\n";
        }
        return result;
    }
}
