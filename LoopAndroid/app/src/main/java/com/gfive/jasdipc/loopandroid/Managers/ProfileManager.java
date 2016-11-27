package com.gfive.jasdipc.loopandroid.Managers;

import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by JasdipC on 2016-10-25.
 */
public class ProfileManager {

    private static ProfileManager profileManager;
    private UserProfile userProfile;
    private FirebaseUser firebaseUser;
    private LoopUser loopUser;

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
        loopUser = new LoopUser();
    }

    public void setLocalUser(FirebaseUser profile) {

        userProfile.id = profile.getUid();
        userProfile.name = profile.getDisplayName();
        userProfile.email = profile.getEmail();
        userProfile.profilePictureURI = profile.getPhotoUrl().toString();

        firebaseUser = profile;

    }

    public void setupLoopUser(String phoneNumber) {
        userProfile.phoneNumber = phoneNumber;

        loopUser.setUuid(userProfile.id);
        loopUser.setName(userProfile.name);
        loopUser.setEmail(userProfile.email);
        loopUser.setPhoto(userProfile.profilePictureURI);
        loopUser.setPhoneNumber(phoneNumber);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public FirebaseUser getFirebaseUser() {return firebaseUser;}

    public LoopUser getLoopUser() {return loopUser;}
}
