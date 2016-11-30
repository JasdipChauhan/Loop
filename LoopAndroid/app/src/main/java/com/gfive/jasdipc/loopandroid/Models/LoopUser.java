package com.gfive.jasdipc.loopandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JasdipC on 2016-10-29.
 */

public class LoopUser implements Parcelable {

    private String uuid;
    private String email;
    private String name;
    private String phoneNumber;
    private String photo;

    //CONSTRUCTORS

    public LoopUser() {
        uuid = "";
        email = "";
        name = "";
        phoneNumber = "";
        photo = "";
    }

    public LoopUser(String uuid, String email, String name, String phoneNumber, String photo) {
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    //GETTER AND SETTERS

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static Creator<LoopUser> getCREATOR() {
        return CREATOR;
    }

    //HELPER METHODS

    //Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.photo);
    }

    protected LoopUser(Parcel in) {
        this.uuid = in.readString();
        this.email = in.readString();
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<LoopUser> CREATOR = new Creator<LoopUser>() {
        @Override
        public LoopUser createFromParcel(Parcel source) {
            return new LoopUser(source);
        }

        @Override
        public LoopUser[] newArray(int size) {
            return new LoopUser[size];
        }
    };
}
