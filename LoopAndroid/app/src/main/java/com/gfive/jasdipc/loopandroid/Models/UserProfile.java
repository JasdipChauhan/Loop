package com.gfive.jasdipc.loopandroid.Models;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


import com.facebook.AccessToken;

import java.io.InputStream;

public class UserProfile {


    public String firstName;
    public String lastName;
    public String middleName;
    public Uri profilePictureURI;

    public AccessToken accessToken;
    public String id;
    public String name;
    public String email;
    public String phoneNumber;

    public UserProfile() {
    }

}
