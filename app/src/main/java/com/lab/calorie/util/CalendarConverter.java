package com.lab.calorie.util;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarConverter {

    @TypeConverter
    public static Calendar toCalendar(String calendarString) {
        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date date = formatter.parse(calendarString);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    @TypeConverter
    public static String toCalendarString(Calendar calendar) {
        return calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }

}
