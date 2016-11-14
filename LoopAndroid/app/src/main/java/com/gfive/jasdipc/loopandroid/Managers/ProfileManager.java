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

        userProfile.id = profile.getUid();
        userProfile.name = profile.getDisplayName();
        userProfile.email = profile.getEmail();
        userProfile.profilePictureURI = profile.getPhotoUrl().toString();

        firebaseUser = profile;
    }

    public void setPhoneNumber(String phoneNumber) {
        userProfile.phoneNumber = phoneNumber;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public FirebaseUser getFirebaseUser() {return firebaseUser;}
}
