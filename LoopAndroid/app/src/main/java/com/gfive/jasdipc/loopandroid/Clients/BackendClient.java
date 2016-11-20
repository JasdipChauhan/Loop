package com.gfive.jasdipc.loopandroid.Clients;

import android.util.Log;

import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerLookup;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerPassback;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;

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

    public void cleanDatabase() {

        final Query query = mRideDatabase.orderByChild("date");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {

                    DataSnapshot rideSnapshot = iterator.next();

                    LoopRide ride = rideSnapshot.getValue(LoopRide.class);
                    Date rideDate = new Date(ride.getDate());
                    Date rightNow = new Date();

                    //we have come to the soonest ride that has not passed, so stop iterating
                    if (rideDate.after(rightNow)) {
                        break;
                    } else {
                        rideSnapshot.getRef().removeValue();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    public void doesUserExist(final UserProfile user, final ServerAction callback) {

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.id)) {
                    String phoneNumber = dataSnapshot.child(user.id).child("phoneNumber").getValue().toString();
                    ProfileManager.getInstance().setupLoopUser(phoneNumber);
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

    public void registerUser(FirebaseUser user, String phoneNumber, final ServerAction callback) {

        try {

            DatabaseReference userRef = mUserDatabase.child(user.getUid());

            LoopUser loopUser = new LoopUser(user.getEmail(),
                    user.getDisplayName(),
                    phoneNumber,
                    user.getPhotoUrl().toString());

            userRef.setValue(loopUser);

            callback.response(true);
        } catch (Exception e) {
            e.printStackTrace();
            callback.response(false);
        }
    }

    public void uploadRide(final ServerPassback callback, final LoopRide loopRide) {

        boolean onCreateSuccess = true;
        DatabaseReference ride = mRideDatabase.push();
        String rideKey = "";

        try {

            ride.setValue(loopRide);
            rideKey = ride.getKey();

        } catch (Exception e) {

            e.printStackTrace();
            onCreateSuccess = false;
        }

        callback.response(onCreateSuccess, rideKey);
    }

    public void reserveRide(final String rideID, final UserProfile user, final ServerAction callback) {

        mRideDatabase.child(rideID).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                LoopRide ride = mutableData.getValue(LoopRide.class);

                if (ride == null) {
                    Log.e("BACKEND", "COULD NOT RETRIEVE RIDE");
                    callback.response(false);
                    return Transaction.success(mutableData);
                }

                if (ride.getSeatsLeft() < 1) {
                    Log.e("BACKEND", "Ride has no seats left");
                    callback.response(false);
                    return Transaction.success(mutableData);
                }

                int newSeats = ride.getSeatsLeft() - 1;
                ride.setSeatsLeft(newSeats);

                LoopUser loopUser = ProfileManager.getInstance().getLoopUser();
                ride.getRiders().put(user.id, loopUser);

                mutableData.setValue(ride);

                callback.response(true);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("BACKEND UPLOAD", "postTransaction:onComplete:" + databaseError);
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

                LoopUser user = dataSnapshot.getValue(LoopUser.class);

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
