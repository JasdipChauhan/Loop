package com.gfive.jasdipc.loopandroid.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class DateFormatter {

    public static String getReadableDate(String dateString) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            sdf = new SimpleDateFormat("MMM dd, yyyy");
            String readableString = sdf.format(date);

            return readableString;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

}
