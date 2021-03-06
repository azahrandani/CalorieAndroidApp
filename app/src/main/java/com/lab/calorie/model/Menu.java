package com.lab.calorie.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lab.calorie.util.CalendarConverter;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Calendar;

@Entity(tableName = "menu_table",
        indices = {@Index(value = "calendar", unique = true)})
public class Menu implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "calendar")
    @TypeConverters(CalendarConverter.class)
    private Calendar calendar;

    @ColumnInfo(name = "calorieValue")
    private int calorieValue;

    @ColumnInfo(name = "bmrValue")
    private int bmrValue;

    private String calendarInNames;
    private String calendarInSlash;

    public Menu(Calendar calendar, int calorieValue, int bmrValue) {
        this.calendar = calendar;
        this.calorieValue = calorieValue;
        this.bmrValue = bmrValue;
        setCalendarInNames();
        setCalendarInSlash();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(int calorieValue) {
        this.calorieValue = calorieValue;
    }

    public int getBmrValue() {
        return bmrValue;
    }

    public void setBmrValue(int bmrValue) {
        this.bmrValue = bmrValue;
    }

    public void setCalendarInNames(String calendarInNames) {
        this.calendarInNames = calendarInNames;
    }

    public void setCalendarInSlash(String calendarInSlash) {
        this.calendarInSlash = calendarInSlash;
    }

    public String getCalendarInNames() {
        return calendarInNames;
    }

    public String getCalendarInSlash() {
        return calendarInSlash;
    }

    public void setCalendarInNames() {
        int date = calendar.get(Calendar.DATE);
        String month = getMonthForInt(calendar.get(Calendar.MONTH));
        int year = calendar.get(Calendar.YEAR);

        this.calendarInNames = date + " " + month + " " + year;
    }

    public void setCalendarInSlash() {
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);

        this.calendarInSlash = date + "/" + month + "/" + year;
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
