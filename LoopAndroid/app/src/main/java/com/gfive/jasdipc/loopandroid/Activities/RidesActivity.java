package com.gfive.jasdipc.loopandroid.Activities;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Clients.APIClient;
import com.gfive.jasdipc.loopandroid.Clients.OnServerResponse;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

public class RidesActivity extends AppCompatActivity implements OnServerResponse {

    private RecyclerView ridesRecyclerView;
    private FloatingActionButton addRideFAB;

    private RidesAdapter ridesAdapter;
    private List<User> users = new ArrayList<>();
    private List<Ride> rides = new ArrayList<>();

    private APIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        ridesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        addRideFAB = (FloatingActionButton) findViewById(R.id.add_ride_FAB);

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(this);
        ridesRecyclerView.setLayoutManager(wCLLM);

        ridesAdapter = new RidesAdapter(rides, RidesActivity.this);
        ridesRecyclerView.setAdapter(ridesAdapter);

        apiClient = APIClient.getInstance();

        apiClient.serverGetRides(RidesActivity.this);

        addRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ridesAdapter.clear();
                apiClient.serverGetRides(RidesActivity.this);
            }
        });

    }

    @Override
    public void serverCallback(final Boolean isSuccessful, final Response serverResponse) {

        try {
            String serverRidesJSON = serverResponse.body().string();
            Log.i("RIDES ACTIVITY", serverRidesJSON);
            List<Ride> serverRides = APIClient.getInstance().parseResponse(serverRidesJSON);

            Log.i("CHECKPOINT", "SUCCESSFUL");

            rides.clear();

            for (Ride ride : serverRides) {
                rides.add(ride);
            }

            Log.i("BREAKPOINT", "SUCCESSFUL");

        } catch (Exception e) {
            Log.e("ERROR", "TRYING TO PARSE RESPONSE FIRST TIME");
            e.printStackTrace();
        }

        RidesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ridesAdapter.notifyDataSetChanged();
            }
        });

        /*RidesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ridesAdapter.notifyDataSetChanged();
            }
        });*/

        /*RidesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String jsonString = "";

                try {
                    jsonString = serverResponse.body().string();
                    Log.i("JSON", jsonString);
                    rides = apiClient.parseResponse(jsonString);
                    ridesAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    Log.e("ERROR", "TRYING TO PARSE RESPONSE");
                    e.printStackTrace();
                }

            }
        });*/

    }

    private User createNewUser(String firstName, String lastName, String username, String password, String email, String phoneNumber, Drawable picture) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setProfilePicture(picture);
        users.add(user);
        return user;
    }
}
