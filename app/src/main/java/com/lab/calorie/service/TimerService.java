package com.lab.calorie.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class TimerService extends Service {

    public final static String COUNTDOWN_SERVICE = "com_lab_calorie.countdown_service";
    Intent intent = new Intent(COUNTDOWN_SERVICE);

    CountDownTimer timer = null;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("###Starting timer...");
    }

    private void constructCountdownTimer(final Intent intent, final int seconds, final String exercise) {
        timer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("###Countdown seconds remaining: " + millisUntilFinished/1000);
                intent.putExtra("countdown", millisUntilFinished);
                intent.putExtra("finish", false);
                intent.putExtra("exercise", exercise);
                intent.putExtra("seconds", seconds);
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {
                System.out.println("###Timer finished!");
                intent.putExtra("finish", true);
                sendBroadcast(intent);
            }
        };
        timer.start();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        System.out.println("###Timer cancelled!");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int seconds = (int) intent.getExtras().get("seconds");
        String exercise = (String) intent.getExtras().get("exercise");
        constructCountdownTimer(this.intent, seconds, exercise);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
