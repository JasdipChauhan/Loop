package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.gfive.jasdipc.loopandroid.Helpers.DateFormatter;

import java.util.Date;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class Ride implements Parcelable {
    private int id;
    private User driver;
    private Date date;
    private String pickup;
    private String dropoff;
    private String time;
    private int passengers;
    private double cost;

    public Ride() {

    }

    //GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String dateString) {
        this.date = DateFormatter.formatToDate(dateString);
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

    //Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.driver, flags);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.pickup);
        dest.writeString(this.dropoff);
        dest.writeString(this.time);
        dest.writeInt(this.passengers);
        dest.writeDouble(this.cost);
    }

    protected Ride(Parcel in) {
        this.id = in.readInt();
        this.driver = in.readParcelable(User.class.getClassLoader());
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.pickup = in.readString();
        this.dropoff = in.readString();
        this.time = in.readString();
        this.passengers = in.readInt();
        this.cost = in.readDouble();
    }

    public static final Creator<Ride> CREATOR = new Creator<Ride>() {
        @Override
        public Ride createFromParcel(Parcel source) {
            return new Ride(source);
        }

        @Override
        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };
}
