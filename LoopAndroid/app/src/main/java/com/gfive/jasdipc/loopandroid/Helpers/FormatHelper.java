package com.gfive.jasdipc.loopandroid.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class FormatHelper {

    public static final long MINUTE_MILLI = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
    public static final long HOUR_MILLI = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
    public static final long DAY_MILLI = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);

    //public static final long HOUR_MILLI = 3600*1000; // in milli-seconds.

    public static String getReadableDate(String dateString) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(dateString);
            simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            String readableString = simpleDateFormat.format(date);

            return readableString;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String getReadableTime(String time24) {

        SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");

        try {
            Date date12 = sdf24.parse(time24);
            return sdf12.format(date12);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return time24;
    }

    public static Date getDate(String dateStr) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static long toUploadFormat(String dateStr, String readableTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        long dateLong = 0;

        try {
            Date date = simpleDateFormat.parse(dateStr);
            dateLong = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return addTimeToDate(dateLong, readableTime);
    }

    public static String toReadableFormat(long dateLong) {

        Date date = new Date(dateLong);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            String readableString = simpleDateFormat.format(date);

            return readableString;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static long addTimeToDate(long date, String readableTime) {

        SimpleDateFormat time12Format = new SimpleDateFormat("hh:mm a");

        try {

            Date timeDate = time12Format.parse(readableTime);
            long timeLong = 0;

            timeLong += timeDate.getHours() * HOUR_MILLI;
            timeLong += timeDate.getMinutes() * MINUTE_MILLI;

            date = date + timeLong;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    private static Date getUploadTime(String readableTime) {

        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");

        try {
            return sdf12.parse(readableTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }
}
