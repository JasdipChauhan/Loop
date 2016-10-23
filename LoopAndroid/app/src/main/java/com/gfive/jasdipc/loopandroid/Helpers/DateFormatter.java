package com.gfive.jasdipc.loopandroid.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class DateFormatter {

    public static Date formatToDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatToYYYYMMDD(Date date) {
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return formattedDate;
    }

    public static String formatToString(Date date) {
        return date.toString();
    }

}
