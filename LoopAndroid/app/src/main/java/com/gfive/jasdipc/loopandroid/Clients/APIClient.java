package com.gfive.jasdipc.loopandroid.Clients;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.Authenticator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIClient {

    //DEVELOPMENT
    private final String LOOP_URL = "http://10.0.2.2:8000/api/v1/rides/";

    //private final String LOOP_URL = "https://api.desktoppr.co/1/wallpapers?page=3";
    //RELEASE
    private final String RELEASE_URL = "";

    private static APIClient instance;
    private OkHttpClient networkClient;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }

        return instance;
    }

    private APIClient() {
        networkClient = new OkHttpClient();
    }

    public void getRides(final OnServerResponse serverResponse) {

        try {
            Request request = new Request.Builder()
                    .url(LOOP_URL)
                    .build();

            networkClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            serverResponse.serverCallback(false, null);
                            Log.e("APICLIENT", "CALLBACK ERROR");
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
}
