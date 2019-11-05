package com.lab.calorie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bmr_table")
public class Bmr {

    @ColumnInfo(name = "isMale")
    private boolean isMale;

    @PrimaryKey
    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "value")
    private double value;

    public Bmr(boolean isMale, int age, int height, int weight) {
        this.isMale = isMale;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.value = calculateBmr(isMale, age, height, weight);
    }

    public void setIsMale(boolean male) {
        isMale = male;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public double getValue() {
        return this.value;
    }

    private double calculateBmr(boolean isMale, int age, int height, int weight) {
        double result = 10*weight + 6.25*height - 5*age;
        result = isMale ? result+5 : result-161;
        return result;
    }

}
