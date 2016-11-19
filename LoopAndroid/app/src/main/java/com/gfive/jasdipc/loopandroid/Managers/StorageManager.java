package com.gfive.jasdipc.loopandroid.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by JasdipC on 2016-11-05.
 */
public class StorageManager {

    private static StorageManager storageManager;

    private static final String SHARED_PREFERENCES_NAME = "LOOP SHARED PREFERENCES";
    private static final String DRIVER_KEY = "DRIVER_RIDES";
    private static final String RIDER_KEY = "RIDER_RIDES";

    private Context mContext;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public static StorageManager getInstance(Context mContext) {

        if (storageManager == null) {
            storageManager = new StorageManager(mContext);
        }

        return storageManager;
    }

    private StorageManager(Context mContext) {
        this.mContext = mContext;
        mPref = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    //DRIVER RIDES

    public void saveDriverRides(String rideID) {

        Set<String> savedRides = getDriverRides();
        savedRides.add(rideID);

        mEditor.putStringSet(DRIVER_KEY, savedRides);
        mEditor.commit();

        Log.i("STORAGE MANAGER", "SAVED DRIVER RIDE");

    }

    public Set<String> getDriverRides() {
        Set<String> savedRides = mPref.getStringSet(DRIVER_KEY, new HashSet<String>());

        for (String ride : savedRides) {
            Log.i("Saved Ride:", ride);
        }

        return savedRides;
    }

    //RIDER RIDES

    public void saveRiderRides(String rideID) {

        Set<String> savedRides = getRiderRides();
        savedRides.add(rideID);

        mEditor.putStringSet(RIDER_KEY, savedRides);
        mEditor.commit();

        Log.i("STORAGE MANAGER", "SAVED RIDER RIDE");
    }

    public Set<String> getRiderRides() {

        Set<String> savedRides = mPref.getStringSet(RIDER_KEY, new HashSet<String>());

        for (String ride : savedRides) {
            Log.i("Saved Ride:", ride);
        }

        return savedRides;
    }

}
