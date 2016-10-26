package com.gfive.jasdipc.loopandroid.Models;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


import com.facebook.AccessToken;

import java.io.InputStream;

public class UserProfile {

    public AccessToken accessToken;
    public String facebookID;
    public String name;
    public String firstName;
    public String lastName;
    public String middleName;
    public Uri profilePictureURI;
    public Bitmap profilePictureBitmap;

    public UserProfile() {
    }

    public void setProfilePictureBitmap (Context mContext) {
        try {
            InputStream image_stream = mContext.getContentResolver().openInputStream(profilePictureURI);
            profilePictureBitmap = BitmapFactory.decodeStream(image_stream );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
