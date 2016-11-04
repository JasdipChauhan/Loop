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

    private static ProfileManager profileManager;
    private UserProfile userProfile;

    private final int PROFILE_PICTURE_HEIGHT = 200;
    private final int PROFILE_PICTURE_WIDTH = 200;

    public static ProfileManager getInstance() {
        if (profileManager == null) {
            profileManager = new ProfileManager();
        }

        return profileManager;
    }

    private ProfileManager() {
        userProfile = new UserProfile();
    }

    public void setLocalUser(Profile profile, AccessToken accessToken) {

        userProfile.accessToken = accessToken;
        userProfile.facebookID = profile.getId();
        userProfile.name = profile.getName();
        userProfile.profilePictureURI = profile.getProfilePictureUri(PROFILE_PICTURE_WIDTH, PROFILE_PICTURE_HEIGHT);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

}
