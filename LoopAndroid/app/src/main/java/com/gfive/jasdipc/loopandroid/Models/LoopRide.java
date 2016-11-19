package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class LoopRide implements Parcelable {

    private String car;
    private long date;
    private LoopUser driver;
    private Map<String, LoopUser> riders = new HashMap<>();
    private String dropoff;
    private String dropoffDescription;
    private String pickup;
    private String pickupDescription;
    private double price;
    private int seatsLeft;
    private int seatsSize;
    private String time;

    //CONSTRUCTORS

    public LoopRide() {
        date = 0;
        driver = new LoopUser();
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

    public LoopRide(String car, long date, LoopUser driver, Map<String, LoopUser> riders, String dropoff, String dropoffDescription, String pickup, String pickupDescription, double price, int seatsLeft, int seatsSize, String time) {
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public LoopUser getDriver() {
        return driver;
    }

    public void setDriver(LoopUser driver) {
        this.driver = driver;
    }

    public Map<String, LoopUser> getRiders() {
        return riders;
    }

    public void setRiders(Map<String, LoopUser> riders) {
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

    public static Creator<LoopRide> getCREATOR() {
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
        dest.writeLong(this.date);
        dest.writeParcelable(this.driver, flags);
        dest.writeInt(this.riders.size());
        for (Map.Entry<String, LoopUser> entry : this.riders.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
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

    protected LoopRide(Parcel in) {
        this.car = in.readString();
        this.date = in.readLong();
        this.driver = in.readParcelable(LoopUser.class.getClassLoader());
        int ridersSize = in.readInt();
        this.riders = new HashMap<String, LoopUser>(ridersSize);
        for (int i = 0; i < ridersSize; i++) {
            String key = in.readString();
            LoopUser value = in.readParcelable(LoopUser.class.getClassLoader());
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

    public static final Creator<LoopRide> CREATOR = new Creator<LoopRide>() {
        @Override
        public LoopRide createFromParcel(Parcel source) {
            return new LoopRide(source);
        }

        @Override
        public LoopRide[] newArray(int size) {
            return new LoopRide[size];
        }
    };
}
