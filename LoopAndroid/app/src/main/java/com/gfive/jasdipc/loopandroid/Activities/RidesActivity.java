package com.gfive.jasdipc.loopandroid.Activities;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.ServerResponseModel;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;

import org.json.JSONArray;

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

    private List<ServerResponseModel> responses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        ridesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        addRideFAB = (FloatingActionButton) findViewById(R.id.add_ride_FAB);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        ridesRecyclerView.setLayoutManager(llm);

        ridesAdapter = new RidesAdapter(rides, RidesActivity.this);
        ridesRecyclerView.setAdapter(ridesAdapter);

        apiClient = APIClient.getInstance();

        addRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date now = new Date();

                User user = new User();
                user.setFirstName("JASDIP");
                user.setLastName("CHAUHAN");
                user.setUsername("JAS");
                user.setPassword("PASS");
                user.setEmail("jasdip@gmail.com");
                user.setPhoneNumber("6475273055");
                user.setProfilePicture(getDrawable(R.drawable.image));

                Ride ride = new Ride();
                ride.setDriver(user);
                ride.setDate(now);
                ride.setPickup("MARKHAM");
                ride.setDropoff("WATERLOO");
                ride.setTime("7:30");
                ride.setPassengers(4);
                ride.setCost(7.5);

                rides.add(ride);

                ridesAdapter.notifyItemInserted(rides.size() - 1);

                apiClient.serverGetRides(RidesActivity.this);
            }
        });

    }

    @Override
    public void serverCallback(final Boolean isSuccessful, final Response serverResponse) {

        RidesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String jsonString = "";

                try {
                    jsonString = serverResponse.body().string();
                    Log.i("JSON", jsonString);
                    rides = apiClient.parseResponse(jsonString);
                    ridesAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ERROR", "TRYING TO PARSE RESPONSE");
                }

            }
        });

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
