package com.gfive.jasdipc.loopandroid.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by JasdipC on 2016-10-08.
 */

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private Drawable profilePicture;

    public User() {
    }

    //GETTERS AND SETTERS

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Drawable getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Drawable profilePicture) {
        this.profilePicture = profilePicture;
    }


    //HELPER

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
