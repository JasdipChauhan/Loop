package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRide implements Parcelable {

    private String car;
    private String date;
    private FirebaseDriver driver;
    private Map<String, String> riders = new HashMap<>();
    private String dropoff;
    private String dropoffDescription;
    private String pickup;
    private String pickupDescription;
    private double price;
    private int seatsLeft;
    private int seatsSize;
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
        car = "";
        dropoffDescription = "";
        pickupDescription = "";
    }

    public FirebaseRide(String car, String date, FirebaseDriver driver, Map<String, String> riders, String dropoff, String dropoffDescription, String pickup, String pickupDescription, double price, int seatsLeft, int seatsSize, String time) {
        this.car = car;
        this.date = date;
        this.driver = driver;
        this.riders = riders;
        this.dropoff = dropoff;
        this.dropoffDescription = dropoffDescription;
        this.pickup = pickup;
        this.pickupDescription = pickupDescription;
        this.price = price;
        this.seatsLeft = seatsLeft;
        this.seatsSize = seatsSize;
        this.time = time;
    }

//GETTER AND SETTERS

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

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

    public String getDropoffDescription() {
        return dropoffDescription;
    }

    public void setDropoffDescription(String dropoffDescription) {
        this.dropoffDescription = dropoffDescription;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getPickupDescription() {
        return pickupDescription;
    }

    public void setPickupDescription(String pickupDescription) {
        this.pickupDescription = pickupDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public int getSeatsSize() {
        return seatsSize;
    }

    public void setSeatsSize(int seatsSize) {
        this.seatsSize = seatsSize;
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
        dest.writeString(this.car);
        dest.writeString(this.date);
        dest.writeParcelable(this.driver, flags);
        dest.writeInt(this.riders.size());
        for (Map.Entry<String, String> entry : this.riders.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.dropoff);
        dest.writeString(this.dropoffDescription);
        dest.writeString(this.pickup);
        dest.writeString(this.pickupDescription);
        dest.writeDouble(this.price);
        dest.writeInt(this.seatsLeft);
        dest.writeInt(this.seatsSize);
        dest.writeString(this.time);
    }

    protected FirebaseRide(Parcel in) {
        this.car = in.readString();
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
        this.dropoffDescription = in.readString();
        this.pickup = in.readString();
        this.pickupDescription = in.readString();
        this.price = in.readDouble();
        this.seatsLeft = in.readInt();
        this.seatsSize = in.readInt();
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
