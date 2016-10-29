package com.gfive.jasdipc.loopandroid.Clients;

import android.net.Uri;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jasdip on 2016-10-27.
 */
public class FirebaseClient {

    private static FirebaseClient firebaseClient;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;


    public static FirebaseClient getInstance() {
        if (firebaseClient == null) {
            firebaseClient = new FirebaseClient();
        }

        return firebaseClient;
    }

    private FirebaseClient() {
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ride");
    }

    public boolean uploadUserProfilePicture() {

        boolean isSuccessful = false;



        return isSuccessful;
    }

    public void postRide() {
        DatabaseReference newRide = mDatabase.push();
        newRide.child("driver").setValue("Jasdip");
        newRide.child("pickup").setValue("markham");
        newRide.child("dropoff").setValue("waterloo");
        newRide.child("seats").setValue(5);
        newRide.child("price").setValue(6.7);
    }

    public void uploadRide(ServerResponse callback, final UserProfile profile, JSONObject jsonObject) {

        boolean onCreateSuccess = true;
        DatabaseReference ride = mDatabase.push();

        try {

            DatabaseReference driver = ride.child("driver");

            driver.child("name").setValue(profile.name);
            driver.child("photo").setValue("http://graph.facebook.com/" + profile.facebookID + "/picture?type=large");
            driver.child("email").setValue(jsonObject.get("driver_email").toString());
            driver.child("phone_number").setValue(jsonObject.get("driver_phone_number").toString());
            ride.child("pickup").setValue(jsonObject.get("pickup").toString());
            ride.child("dropoff").setValue(jsonObject.get("dropoff").toString());
            ride.child("date").setValue(jsonObject.get("date").toString());
            ride.child("time").setValue(jsonObject.get("time").toString());
            ride.child("seats_left").setValue(jsonObject.get("seats_left").toString());
            ride.child("price").setValue(jsonObject.get("price").toString());

        } catch (JSONException e) {

            e.printStackTrace();
            onCreateSuccess = false;

        } catch (Exception e) {

            e.printStackTrace();
            onCreateSuccess = false;
        }

        callback.response(onCreateSuccess);
    }

    private void forTestingServices() {

    }
}
