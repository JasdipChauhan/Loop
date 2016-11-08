package com.gfive.jasdipc.loopandroid.Models;

import java.util.ArrayList;
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
