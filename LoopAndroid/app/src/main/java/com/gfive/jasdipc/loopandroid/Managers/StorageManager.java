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
    private static final String RIDE_KEY = "RIDES";
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


    public Set<String> getSavedRides() {

        Set<String> savedRides = mPref.getStringSet(RIDE_KEY, new HashSet<String>());

        return savedRides;
    }

    public void saveRide(String rideID) {

        Set<String> savedRides = getSavedRides();
        savedRides.add(rideID);

        mEditor.putStringSet(RIDE_KEY, savedRides);
        mEditor.commit();

        Log.i("STORAGE MANAGER", "RIDE SAVED");
    }
}
