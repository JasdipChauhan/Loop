package com.gfive.jasdipc.loopandroid.Clients;

import android.net.Uri;

import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by jasdip on 2016-10-27.
 */
public class FirebaseClient {

    private static FirebaseClient firebaseClient;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private StorageReference mStorage;


    public static FirebaseClient getInstance() {
        if (firebaseClient == null) {
            firebaseClient = new FirebaseClient();
        }

        return firebaseClient;
    }

    private FirebaseClient() {
        mStorage = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference().child("");
    }

    public boolean uploadUserProfilePicture() {

        boolean isSuccessful = false;



        return isSuccessful;
    }

    public void postRide() {
        DatabaseReference newRide = mRef.push();
        newRide.child("driver").setValue("Jasdip");
        newRide.child("pickup").setValue("markham");
        newRide.child("dropoff").setValue("waterloo");
        newRide.child("seats").setValue(5);
        newRide.child("price").setValue(6.7);
    }

    public void uploadRide(Uri profilePictureData) {
        String filePathName = ProfileManager.getInstance().getUserProfile().facebookID;

        StorageReference filepath = mStorage.child("Profile_Pictures").child(filePathName);

        filepath.putFile(profilePictureData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                UserProfile userProfile = ProfileManager.getInstance().getUserProfile();

                Uri pictureDownloadURL = taskSnapshot.getDownloadUrl();
                DatabaseReference newRide = mRef.push();

                newRide.child("driver").setValue(userProfile.name);
                newRide.child("pickup").setValue("markham");
                newRide.child("dropoff").setValue("waterloo");
                newRide.child("seats").setValue(5);
                newRide.child("price").setValue(7.5);

            }
        });
    }

    private void forTestingServices() {

    }
}
