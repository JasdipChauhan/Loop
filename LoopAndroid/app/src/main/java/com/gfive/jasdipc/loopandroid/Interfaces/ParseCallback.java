package com.gfive.jasdipc.loopandroid.Interfaces;

import com.gfive.jasdipc.loopandroid.Models.Ride;

import java.util.List;

public interface ParseCallback {
    void retrieveRides(boolean isSuccessful, List<Ride> ridesList);
}
