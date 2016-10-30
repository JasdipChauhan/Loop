package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class FirebaseRide implements Parcelable {

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

    //Parcelable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeParcelable(this.driver, flags);
        dest.writeString(this.dropoff);
        dest.writeString(this.pickup);
        dest.writeDouble(this.price);
        dest.writeInt(this.seatsLeft);
        dest.writeString(this.time);
    }

    protected FirebaseRide(Parcel in) {
        this.date = in.readString();
        this.driver = in.readParcelable(FirebaseDriver.class.getClassLoader());
        this.dropoff = in.readString();
        this.pickup = in.readString();
        this.price = in.readDouble();
        this.seatsLeft = in.readInt();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<FirebaseRide> CREATOR = new Parcelable.Creator<FirebaseRide>() {
        @Override
        public FirebaseRide createFromParcel(Parcel source) {
            return new FirebaseRide(source);
        }

        @Override
        public FirebaseRide[] newArray(int size) {
            return new FirebaseRide[size];
        }
    };
}
