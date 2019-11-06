package com.lab.calorie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodReport {

    @SerializedName("sr")
    private String sr;

    @SerializedName("groups")
    private String groups;

    @SerializedName("subset")
    private String subset;

    @SerializedName("end")
    private int end;

    @SerializedName("start")
    private int start;

    @SerializedName("total")
    private int total;

    @SerializedName("foods")
    private List<FoodJson> foodJsonList;

    public FoodReport(String sr, String groups, String subset, int end, int start, int total, List<FoodJson> foodJsonList) {
        this.sr = sr;
        this.groups = groups;
        this.subset = subset;
        this.end = end;
        this.start = start;
        this.total = total;
        this.foodJsonList = foodJsonList;
    }

    public String getSr() {
        return sr;
    }

    public String getGroups() {
        return groups;
    }

    public String getSubset() {
        return subset;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<FoodJson> getFoodJsonList() {
        return foodJsonList;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public void setSubset(String subset) {
        this.subset = subset;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFoodJsonList(List<FoodJson> foodJsonList) {
        this.foodJsonList = foodJsonList;
    }
}
