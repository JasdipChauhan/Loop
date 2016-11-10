package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRide implements Parcelable {

    private String date;
    private FirebaseDriver driver;
    private Map<String, String> riders = new HashMap<>();
    private String dropoff;
    private String pickup;
    private double price;
    private int seatsSize;
    private int seatsLeft;
    private String time;

    //CONSTRUCTORS

    public FirebaseRide() {
        date = "";
        driver = new FirebaseDriver();
        riders = new HashMap<>();
        dropoff = "";
        pickup = "";
        price = 0;
        seatsLeft = 0;
        time = "";
    }

    public FirebaseRide(String date, FirebaseDriver driver, Map<String, String> riders, String dropoff, String pickup, double price, int seatsSize, int seatsLeft, String time) {
        this.date = date;
        this.driver = driver;
        this.riders = riders;
        this.dropoff = dropoff;
        this.pickup = pickup;
        this.price = price;
        this.seatsSize = seatsSize;
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

    public Map<String, String> getRiders() {
        return riders;
    }

    public void setRiders(Map<String, String> riders) {
        this.riders = riders;
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

    public int getSeatsSize() {
        return seatsSize;
    }

    public void setSeatsSize(int seatsSize) {
        this.seatsSize = seatsSize;
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

    public static Creator<FirebaseRide> getCREATOR() {
        return CREATOR;
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
        dest.writeInt(this.riders.size());
        for (Map.Entry<String, String> entry : this.riders.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.dropoff);
        dest.writeString(this.pickup);
        dest.writeDouble(this.price);
        dest.writeInt(this.seatsSize);
        dest.writeInt(this.seatsLeft);
        dest.writeString(this.time);
    }

    protected FirebaseRide(Parcel in) {
        this.date = in.readString();
        this.driver = in.readParcelable(FirebaseDriver.class.getClassLoader());
        int ridersSize = in.readInt();
        this.riders = new HashMap<String, String>(ridersSize);
        for (int i = 0; i < ridersSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.riders.put(key, value);
        }
        this.dropoff = in.readString();
        this.pickup = in.readString();
        this.price = in.readDouble();
        this.seatsSize = in.readInt();
        this.seatsLeft = in.readInt();
        this.time = in.readString();
    }

    public static final Creator<FirebaseRide> CREATOR = new Creator<FirebaseRide>() {
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
