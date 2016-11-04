package com.gfive.jasdipc.loopandroid.Managers;

import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by JasdipC on 2016-10-25.
 */
public class ProfileManager {

    private static ProfileManager profileManager;
    private UserProfile userProfile;
    private FirebaseUser firebaseUser;

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

    public void setLocalUser(FirebaseUser profile) {

        firebaseUser = profile;

        userProfile.id = profile.getUid();
        userProfile.name = profile.getDisplayName();
        userProfile.profilePictureURI = profile.getPhotoUrl();
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public FirebaseUser getFirebaseUser() {return firebaseUser;}
}
