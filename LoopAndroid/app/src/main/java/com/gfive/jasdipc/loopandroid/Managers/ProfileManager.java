package com.gfive.jasdipc.loopandroid.Managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;

/**
 * Created by JasdipC on 2016-10-25.
 */
public class ProfileManager {

    private Context mContext;
    private static ProfileManager profileManager;
    private UserProfile userProfile;

    public static ProfileManager getInstance(Context mContext) {
        if (profileManager == null) {
            profileManager = new ProfileManager(mContext);
        }

        return profileManager;
    }

    private ProfileManager(Context mContext) {
        this.mContext = mContext;
        userProfile = new UserProfile();
    }

    public void register(Profile profile, AccessToken accessToken) {

        userProfile.accessToken = accessToken;
        userProfile.facebookID = profile.getId();
        userProfile.name = profile.getName();
        userProfile.profilePictureURI = profile.getProfilePictureUri(200, 200);
        userProfile.setProfilePictureBitmap(mContext);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
