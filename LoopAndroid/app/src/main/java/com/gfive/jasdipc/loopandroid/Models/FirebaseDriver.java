package com.gfive.jasdipc.loopandroid.Models;

/**
 * Created by JasdipC on 2016-10-29.
 */

public class FirebaseDriver {

    private String email;
    private String name;
    private String phone_number;
    private String photo;

    //CONSTRUCTORS

    public FirebaseDriver() {

    }

    public FirebaseDriver(String email, String name, String phone_number, String photo) {
        this.email = email;
        this.name = name;
        this.phone_number = phone_number;
        this.photo = photo;
    }

    //GETTER AND SETTERS

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
