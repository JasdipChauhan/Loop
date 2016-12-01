package com.gfive.jasdipc.loopandroid.Managers;

import com.facebook.login.LoginManager;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by JasdipC on 2016-10-25.
 */
public class ProfileManager {

    private static ProfileManager profileManager;
    private FirebaseUser firebaseUser;
    private LoopUser loopUser;

    private FirebaseAuth mFirebaseAuth;

    private final int PROFILE_PICTURE_HEIGHT = 200;
    private final int PROFILE_PICTURE_WIDTH = 200;

    public static ProfileManager getInstance() {
        if (profileManager == null) {
            profileManager = new ProfileManager();
        }

        return profileManager;
    }

    private ProfileManager() {
        loopUser = new LoopUser();
    }

    public void setLocalUser(FirebaseUser profile) {
        firebaseUser = profile;
    }

    public void setupLoopUser(String phoneNumber) {

        loopUser.setUuid(firebaseUser.getUid());
        loopUser.setName(firebaseUser.getDisplayName());
        loopUser.setEmail(firebaseUser.getEmail());
        loopUser.setPhoto(firebaseUser.getPhotoUrl().toString());
        loopUser.setPhoneNumber(phoneNumber);
    }

    public FirebaseUser getFirebaseUser() {return firebaseUser;}

    public LoopUser getLoopUser() {return loopUser;}

    //AUTHENTICATION

    //sign out of all authentication providers
    public void signOut () {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
}
