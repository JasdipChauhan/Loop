package com.gfive.jasdipc.loopandroid.Clients;

import android.util.Log;

import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jasdip on 2016-10-27.
 */
public class BackendClient {

    private static BackendClient backendClient;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;


    public static BackendClient getInstance() {
        if (backendClient == null) {
            backendClient = new BackendClient();
        }

        return backendClient;
    }

    private BackendClient() {
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ride");
    }

    public void reserveRide(final String rideID, final ServerResponse callback) {

        DatabaseReference rideRef = mDatabase.child(rideID).child("seatsLeft");

        rideRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int seatsLeft;
                try {
                    seatsLeft = dataSnapshot.getValue(Integer.class);

                } catch (Exception e) {
                    e.printStackTrace();
                    callback.response(false);
                    return;
                }

                seatsLeft--;


                if (seatsLeft > 0 ) {

                    mDatabase.child(rideID).child("seatsLeft").setValue(seatsLeft);
                    callback.response(true);
                } else {
                    mDatabase.child(rideID).removeValue();
                    callback.response(true);
                }

                callback.response(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                Log.e("API CODE:", Integer.toString(databaseError.getCode()));
                Log.e("API MESSAGE:", databaseError.getMessage());
                Log.e("API DETAILS:", databaseError.getDetails());

                callback.response(false);
            }
        });

    }


    public void uploadRide(ServerResponse callback, final UserProfile profile, JSONObject jsonObject) {

        boolean onCreateSuccess = true;
        DatabaseReference ride = mDatabase.push();

        try {

            DatabaseReference driver = ride.child("driver");

            driver.child("name").setValue(profile.name);
            driver.child("photo").setValue(profile.profilePictureURI.toString());
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
