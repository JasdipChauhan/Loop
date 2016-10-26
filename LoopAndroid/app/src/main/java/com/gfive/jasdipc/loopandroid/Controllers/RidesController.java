package com.gfive.jasdipc.loopandroid.Controllers;

import android.util.Log;

import com.gfive.jasdipc.loopandroid.Clients.APIClient;
import com.gfive.jasdipc.loopandroid.Interfaces.OnServerResponse;
import com.gfive.jasdipc.loopandroid.Interfaces.ParseCallback;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by JasdipC on 2016-10-14.
 */

public class RidesController implements OnServerResponse {

    private APIClient apiClient;
    private ParseCallback parseCallback;

    public RidesController() {
        apiClient = APIClient.getInstance();
    }

    public void refreshRidesList(ParseCallback parseCallback) {
        this.parseCallback = parseCallback;
        apiClient.serverGetRides(RidesController.this);
    }

    @Override
    public void serverCallback(Boolean isSuccessful, Response serverResponse) {

        String serverResponseString = "";
        List<Ride> ridesList = new ArrayList<>();

        if (isSuccessful) {

            try {
                serverResponseString = serverResponse.body().string();

                if (serverResponseString.isEmpty()) {
                    apiClient.serverGetRides(RidesController.this);
                    return;
                }
                ridesList = parseResponse(serverResponseString);

            } catch (IOException ioE) {
                ioE.printStackTrace();
            }
        }

        parseCallback.retrieveRides(isSuccessful, ridesList);
    }

    private List<Ride> parseResponse(String jsonString) {

        List<Ride> rides = new ArrayList<>();

        try {
            JSONArray responsesArray = new JSONArray(jsonString);

            for (int i = 0; i < responsesArray.length(); i++) {

                JSONObject object = responsesArray.getJSONObject(i);

                Ride tempRide = new Ride();
                User tempUser = new User();

                tempUser.setName(object.getString("driver_name"));
                tempUser.setEmail(object.getString("driver_email"));
                tempUser.setPhoneNumber(object.getString("driver_phone_number"));

                tempRide.setId(object.getInt("id"));
                tempRide.setPickup(object.getString("pickup"));
                tempRide.setDropoff(object.getString("dropoff"));
                tempRide.setDate(object.getString("date"));
                tempRide.setTime(object.getString("time"));
                tempRide.setPassengers(object.getInt("seats_left"));
                tempRide.setCost(object.getDouble("price"));

                if (tempRide.getPassengers() == 0) {
                    apiClient.deleteRide(RidesController.this, tempRide.getId());
                    return rides;
                }

                tempRide.setDriver(tempUser);
                rides.add(tempRide);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("RIDESACTIVITY", "ERROR POPULATING LIST");
        }

        return rides;
    }
}
