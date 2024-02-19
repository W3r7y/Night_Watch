package com.example.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static String calculateDifferenceBetweenTime(String t1, String t2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date time1 = format.parse(t1);
            Date time2 = format.parse(t2);

            // Calculate the difference in milliseconds
            long differenceMillis = time2.getTime() - time1.getTime();

            // Handle differences across midnight
            if (differenceMillis < 0) {
                differenceMillis += 24 * 60 * 60 * 1000; // Add 24 hours
            }

            // Convert milliseconds to hours and minutes
            long hours = differenceMillis / (60 * 60 * 1000);
            long minutes = (differenceMillis / (60 * 1000)) % 60;

            // Format the result as "HH:mm"
            return String.format("%02d:%02d", hours, minutes);

        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid time format";
        }
    }
    public static int calculateShiftTimeInMinnutes(String totalTime, int num_of_people){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date time = format.parse(totalTime);
            int hours = getHourOfDate(time);
            int minutes = getMinutesOfDate(time);
            int totalMinutes = (hours * 60) + minutes;
            int minutesPerShift =  Math.round(totalMinutes / num_of_people);
            return minutesPerShift;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getHourOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutesOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

}
