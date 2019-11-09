package com.lab.calorie;

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
        System.out.println("##toCalendar of " + calendarString);
        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date date = formatter.parse(calendarString);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("##toCalendar result: " + calendar.toString());
        return calendar;
    }

    @TypeConverter
    public static String toCalendarString(Calendar calendar) {
        System.out.println("##toCalendarString");
        System.out.println("##getDay: " + calendar.get(Calendar.DATE));
        System.out.println("##getMonth: " + calendar.get(Calendar.MONTH));
        System.out.println("##getYear: " + calendar.get(Calendar.YEAR));
        return calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }

}
