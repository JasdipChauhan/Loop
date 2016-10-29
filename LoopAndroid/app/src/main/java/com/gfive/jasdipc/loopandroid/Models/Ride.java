package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.gfive.jasdipc.loopandroid.Helpers.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class Ride {

    public UserProfile driver;
    public String date;
    public String time;
    public String pickup;
    public String dropoff;
    public double price;
    public int seats;
    public List<UserProfile> riders;

    public Ride() {
        riders = new ArrayList<>();
    }

    //GETTERS AND SETTERS

}
