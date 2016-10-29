package com.gfive.jasdipc.loopandroid.Models;

public class FirebaseRide {

    private String date;
    private FirebaseDriver driver;
    private String dropoff;
    private String pickup;
    private double price;
    private int seatsLeft;
    private String time;

    //CONSTRUCTORS

    public FirebaseRide() {

    }

    public FirebaseRide(String date, FirebaseDriver driver, String dropoff, String pickup, Double price, int seatsLeft, String time) {
        this.date = date;
        this.driver = driver;
        this.dropoff = dropoff;
        this.pickup = pickup;
        this.price = price;
        this.seatsLeft = seatsLeft;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
