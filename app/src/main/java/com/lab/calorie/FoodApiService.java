package com.lab.calorie;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodApiService extends Service {

    private static String API_URL;

    public FoodApiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        API_URL = "https://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=XTq2zCQifXnxMuDXUSB0scIWcJvKF2UMFjT3fAj8&nutrients=208&subset=1&max=20";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FoodApiCaller foodApiCaller = new FoodApiCaller();
        foodApiCaller.execute(API_URL);
        return START_NOT_STICKY;
    }
}
