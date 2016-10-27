package com.gfive.jasdipc.loopandroid.Clients;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
}
