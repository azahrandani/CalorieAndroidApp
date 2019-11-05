package com.lab.calorie;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodApiCaller extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        String output = "";
        String line;

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            BufferedReader br;

            if (statusCode == 200 || statusCode == 201) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            while ((line = br.readLine()) != null) {
                output += line;
            }
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("about to return the output from background!");
        return output;
    }
}
