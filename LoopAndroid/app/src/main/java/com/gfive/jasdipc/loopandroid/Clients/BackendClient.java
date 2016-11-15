package com.gfive.jasdipc.loopandroid.Clients;

import android.net.Uri;
import android.util.Log;

import com.gfive.jasdipc.loopandroid.Interfaces.ServerLookup;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by jasdip on 2016-10-27.
 */
public class BackendClient {

    private static BackendClient backendClient;

    private DatabaseReference mRideDatabase;
    private DatabaseReference mUserDatabase;
    private StorageReference mStorage;


    public static BackendClient getInstance() {
        if (backendClient == null) {
            backendClient = new BackendClient();
        }

        return backendClient;
    }

    private BackendClient() {
        mStorage = FirebaseStorage.getInstance().getReference();
        mRideDatabase = FirebaseDatabase.getInstance().getReference().child("Ride");
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void doesUserExist(final UserProfile user, final ServerResponse callback) {

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.id)) {
                    String phoneNumber = dataSnapshot.child(user.id).child("phoneNumber").getValue().toString();
                    ProfileManager.getInstance().setPhoneNumber(phoneNumber);
                    callback.response(true);
                } else {
                    callback.response(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                Log.e("BACKEND DOES USER EXIST", "CANCELLED");
                callback.response(false);

            }
        });

    }

    public void registerUser(FirebaseUser user, String phoneNumber, final ServerResponse callback) {

        try {

            DatabaseReference userRef = mUserDatabase.child(user.getUid());
            userRef.child("name").setValue(user.getDisplayName());
            userRef.child("email").setValue(user.getEmail());
            userRef.child("phoneNumber").setValue(phoneNumber);
            userRef.child("photo").setValue(user.getPhotoUrl().toString());

            callback.response(true);
        } catch (Exception e) {
            e.printStackTrace();
            callback.response(false);
        }
    }

    public void uploadRide(ServerResponse callback, final UserProfile profile, JSONObject jsonObject) {

        boolean onCreateSuccess = true;
        DatabaseReference ride = mRideDatabase.push();

        try {

            DatabaseReference driver = ride.child("driver");

            driver.child("name").setValue(profile.name);
            driver.child("photo").setValue(profile.profilePictureURI.toString());
            driver.child("email").setValue(profile.email);
            driver.child("phoneNumber").setValue(profile.phoneNumber);

            ride.child("pickup").setValue(jsonObject.getString("pickup").toString());
            ride.child("pickupDescription").setValue(jsonObject.getString("pickupDescription"));
            ride.child("dropoff").setValue(jsonObject.getString("dropoff").toString());
            ride.child("dropoffDescription").setValue(jsonObject.getString("dropoffDescription"));
            ride.child("date").setValue(jsonObject.getString("date").toString());
            ride.child("time").setValue(jsonObject.getString("time").toString());
            ride.child("car").setValue(jsonObject.getString("car").toString());
            ride.child("seatsSize").setValue(jsonObject.getInt("seats"));
            ride.child("seatsLeft").setValue(jsonObject.getInt("seats"));
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

    public void reserveRide(final String rideID, final UserProfile user, final ServerResponse callback) {

        mRideDatabase.child(rideID).child("seatsLeft").addListenerForSingleValueEvent(new ValueEventListener() {
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

                    mRideDatabase.child(rideID).child("seatsLeft").setValue(seatsLeft);

                    DatabaseReference riderRef = mRideDatabase.child(rideID).child("riders").child(user.id);
                    riderRef.child("email").setValue(user.email);
                    riderRef.child("name").setValue(user.name);
                    riderRef.child("phoneNumber").setValue(user.phoneNumber);
                    riderRef.child("photo").setValue(user.profilePictureURI.toString());

                    callback.response(true);
                } else {
                    //mRideDatabase.child(rideID).removeValue();
                    callback.response(false);
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

    //ON PRESENTATION LAYER
    //    List<String> riders = getRiderList(model.getRiders());
    //
    //    for (String riderID : riders) {
    //
    //        BackendClient.getInstance().userLookup(riderID, new ServerLookup() {
    //            @Override
    //            public void onLookup(UserProfile userProfile) {
    //                Picasso.with(mContext).load(userProfile.profilePictureURI).into(holder.riderImage);
    //            }
    //        });
    //    }


    public void userLookup(String riderID, final ServerLookup callback) {

        mUserDatabase.child(riderID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserProfile user = new UserProfile();

                user.email = dataSnapshot.child("email").getValue(String.class);
                user.name = dataSnapshot.child("name").getValue(String.class);
                user.phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                user.profilePictureURI = dataSnapshot.child("photo").getValue(String.class);

                callback.onLookup(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                callback.onLookup(null);
            }
        });

    }

}
