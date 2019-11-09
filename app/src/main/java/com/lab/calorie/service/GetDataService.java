package com.lab.calorie.service;

import com.lab.calorie.util.CompleteFoodJson;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    String URL = "/ndb/nutrients/?format=json&api_key=XTq2zCQifXnxMuDXUSB0scIWcJvKF2UMFjT3fAj8&nutrients=208&subset=1&max=200";

    @GET(URL)
    Call<CompleteFoodJson> getFoodJson();
}
