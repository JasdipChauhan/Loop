package com.gfive.jasdipc.loopandroid.Clients;

import android.util.Log;

import com.gfive.jasdipc.loopandroid.Interfaces.OnServerResponse;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class APIClient {

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final int ENDPOINT_ERROR = 1;
    private final int RESPONSE_ERROR = 2;
    private final int NETWORK_CLIENT_ERROR = 3;

    private String AUTHORIZATION_HEADER_TITLE = "Authorization";
    //DEVELOPMENT
    private final String LOOP_URL = "http://10.0.2.2:8000/api/v1/rides/";

    //RELEASE
    private final String RELEASE_URL = "";

    private static APIClient instance;
    private OkHttpClient networkClient;


    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }

        return instance;
    }

    private APIClient() {
        networkClient = new OkHttpClient();
        //authenticate();
    }

    //GET
    public void serverGetRides(final OnServerResponse serverResponse) {

        try {
            Request request = new Request.Builder()
                    .url(LOOP_URL)
                    .build();

            networkClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            Log.e("APICLIENT", "NETWORK ONFAILURE");
                            e.printStackTrace();
                            serverResponse.serverCallback(false, null);

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String responseString = response.body().toString();
                                serverResponse.serverCallback(true, response);

                            } else {

                                serverResponse.serverCallback(false, null);
                                Log.e("APICLIENT", "RESPONSE UNSUCCESSFUL");
                            }

                            IOUtils.closeQuietly(response);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("APICLIENT", "TRY SWALLOWED EXCEPTION");

            serverResponse.serverCallback(false, null);
        }
    }

    //PUT
    public void updateRide(final OnServerResponse serverResponse, JSONObject updateRideJSON, int rideID) {
        String updateURL = LOOP_URL + rideID + "/";

        RequestBody body = RequestBody.create(JSON, updateRideJSON.toString());
        //String credentials = Credentials.basic("loopuser", "looploop");
        String credentials = Credentials.basic("jasdipc", "Gwtv88sc");

        try {
            Request request = new Request.Builder()
                    .addHeader(AUTHORIZATION_HEADER_TITLE, credentials)
                    .url(updateURL)
                    .put(body)
                    .build();

            networkClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    serverResponse.serverCallback(false, null);
                    Log.e("APICLIENT", "HTTP ERROR");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        serverResponse.serverCallback(true, response);
                    } else {
                        Log.e("APICLIENT", "RESPONSE ERROR");
                        serverResponse.serverCallback(false, response);
                    }

                    IOUtils.closeQuietly(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("APICLIENT", "TRY SWALLOWED EXCEPTION");

            serverResponse.serverCallback(false, null);
        }
    }

    //POST
    public void postRide(final OnServerResponse serverResponse, final JSONObject postBody) {

        RequestBody body = RequestBody.create(JSON, postBody.toString());
        String credentials = Credentials.basic("loopuser", "looploop");

        try {
            Request request = new Request.Builder()
                    .addHeader(AUTHORIZATION_HEADER_TITLE, credentials)
                    .url(LOOP_URL)
                    .post(body)
                    .build();

            networkClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    serverResponse.serverCallback(false, null);
                    Log.e("APICLIENT", "HTTP ERROR");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        serverResponse.serverCallback(true, response);
                    } else {
                        Log.e("APICLIENT", "RESPONSE ERROR");
                        serverResponse.serverCallback(false, response);
                    }

                    IOUtils.closeQuietly(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("APICLIENT", "TRY SWALLOWED EXCEPTION");

            serverResponse.serverCallback(false, null);
        }

    }

    //DELETE
    public void deleteRide(final OnServerResponse serverResponse, final int rideID) {

        String deleteURL = LOOP_URL + rideID + "/";
        String credentials = Credentials.basic("jasdipc", "Gwtv88sc");

        try {
            Request request = new Request.Builder()
                    .addHeader(AUTHORIZATION_HEADER_TITLE, credentials)
                    .url(deleteURL)
                    .delete()
                    .build();

            networkClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    serverResponse.serverCallback(false, null);
                    Log.e("APICLIENT", "HTTP ERROR");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        serverResponse.serverCallback(true, response);
                    } else {
                        Log.e("APICLIENT", "RESPONSE ERROR");
                        serverResponse.serverCallback(false, response);
                    }

                    IOUtils.closeQuietly(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("APICLIENT", "TRY SWALLOWED EXCEPTION");

            serverResponse.serverCallback(false, null);
        }

    }

    private String getBreakdownMessage(int breakdownPoint) {
        String breakdownMessage = "";

        switch (breakdownPoint) {
            case ENDPOINT_ERROR:
                breakdownMessage = "SERVER NOT HITTING ENDPOINT";
                break;

            case RESPONSE_ERROR:
                breakdownMessage = "SERVER RESPONSE ERROR";
                break;

            case NETWORK_CLIENT_ERROR:
                breakdownMessage = "OKHTTP CONNECTION FAULT";
                break;

            default:

        }

        return breakdownMessage;
    }
}
