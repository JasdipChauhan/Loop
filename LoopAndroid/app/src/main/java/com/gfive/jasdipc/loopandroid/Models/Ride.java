package com.gfive.jasdipc.loopandroid.Models;

import java.util.Date;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class Ride {
    private User driver;
    private Date date;
    private String pickup;
    private String dropoff;
    private String time;
    private int passengers;
    private double cost;

    public Ride(User driver, Date date, String pickup, String dropoff, String time, int passengers, double cost) {
        this.driver = driver;
        this.date = date;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.time = time;
        this.passengers = passengers;
        this.cost = cost;
    }

    //GETTERS AND SETTERS

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
