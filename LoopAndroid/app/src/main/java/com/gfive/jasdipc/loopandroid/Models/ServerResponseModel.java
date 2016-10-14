package com.gfive.jasdipc.loopandroid.Models;

/**
 * Created by JasdipC on 2016-10-14.
 */

public class ServerResponseModel {


    /**
     * id : 1
     * driver_name : Jasdip Chauhan
     * driver_email : jasdip.chauhan@gmail.com
     * driver_phone_number : 6475273055
     * pickup : Markham
     * destination : Waterloo
     * date : 2016-10-14
     * time : 7:30
     * seats_left : 4
     * price : 10.50
     * created : 2016-10-13T00:16:17.442968Z
     */

    private int id;
    private String driver_name;
    private String driver_email;
    private String driver_phone_number;
    private String pickup;
    private String destination;
    private String date;
    private String time;
    private int seats_left;
    private Double price;
    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public void setDriver_email(String driver_email) {
        this.driver_email = driver_email;
    }

    public String getDriver_phone_number() {
        return driver_phone_number;
    }

    public void setDriver_phone_number(String driver_phone_number) {
        this.driver_phone_number = driver_phone_number;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeats_left() {
        return seats_left;
    }

    public void setSeats_left(int seats_left) {
        this.seats_left = seats_left;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
