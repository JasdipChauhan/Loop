package com.gfive.jasdipc.loopandroid.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class FormatHelper {

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

    public static long toUploadFormat(String dateStr) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        long dateLong = 0;

        try {
            Date date = simpleDateFormat.parse(dateStr);
            dateLong = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateLong;
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
}
