package com.gfive.jasdipc.loopandroid.Models;

public class FirebaseRide {

    private String date;
    private FirebaseDriver driver;
    private String dropoff;
    private String pickup;
    private double price;
    private int seats_left;
    private String time;

    //CONSTRUCTORS

    public FirebaseRide() {

    }

    public FirebaseRide(String date, FirebaseDriver driver, String dropoff, String pickup, double price, int seats_left, String time) {
        this.date = date;
        this.driver = driver;
        this.dropoff = dropoff;
        this.pickup = pickup;
        this.price = price;
        this.seats_left = seats_left;
        this.time = time;
    }

    //GETTER AND SETTERS

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FirebaseDriver getDriver() {
        return driver;
    }

    public void setDriver(FirebaseDriver driver) {
        this.driver = driver;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeats_left() {
        return seats_left;
    }

    public void setSeats_left(int seats_left) {
        this.seats_left = seats_left;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
