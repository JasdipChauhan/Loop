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

    public void uploadRide(ServerResponse callback, final UserProfile profile, JSONObject jsonObject) {

        boolean onCreateSuccess = true;
        DatabaseReference ride = mDatabase.push();

        try {

            DatabaseReference driver = ride.child("driver");

            driver.child("name").setValue(profile.name);
            driver.child("photo").setValue(profile.profilePictureString);
            driver.child("email").setValue(jsonObject.getString("email").toString());
            driver.child("phoneNumber").setValue(jsonObject.getString("phoneNumber").toString());
            ride.child("pickup").setValue(jsonObject.getString("pickup").toString());
            ride.child("dropoff").setValue(jsonObject.getString("dropoff").toString());
            ride.child("date").setValue(jsonObject.getString("date").toString());
            ride.child("time").setValue(jsonObject.getString("time").toString());
            ride.child("seatsLeft").setValue(jsonObject.getInt("seatsLeft"));
            ride.child("price").setValue(jsonObject.getDouble("price"));

        } catch (JSONException e) {

            e.printStackTrace();
            onCreateSuccess = false;

        } catch (Exception e) {

            e.printStackTrace();
            onCreateSuccess = false;
        }

        callback.response(onCreateSuccess);
    }
}
