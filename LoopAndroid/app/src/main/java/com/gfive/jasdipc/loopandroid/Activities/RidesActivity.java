package com.gfive.jasdipc.loopandroid.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Clients.APIClient;
import com.gfive.jasdipc.loopandroid.Controllers.RidesController;
import com.gfive.jasdipc.loopandroid.Interfaces.OnServerResponse;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Interfaces.ParseCallback;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class RidesActivity extends AppCompatActivity implements ParseCallback{

    private RecyclerView ridesRecyclerView;
    private FloatingActionButton addRideFAB;
    private FloatingActionButton refreshRideFAB;

    private RidesAdapter ridesAdapter;
    private List<User> users = new ArrayList<>();
    private List<Ride> rides = new ArrayList<>();

    private RidesController ridesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        ridesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        addRideFAB = (FloatingActionButton) findViewById(R.id.add_ride_FAB);
        refreshRideFAB = (FloatingActionButton) findViewById(R.id.refresh_ride_FAB);

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(this);
        ridesRecyclerView.setLayoutManager(wCLLM);

        ridesAdapter = new RidesAdapter(rides, RidesActivity.this);
        ridesRecyclerView.setAdapter(ridesAdapter);

        ridesController = new RidesController();
        ridesController.refreshRidesList(RidesActivity.this);

        addRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        refreshRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ridesAdapter.clear();
                ridesController.refreshRidesList(RidesActivity.this);
            }
        });

    }

    @Override
    public void retrieveRides(boolean isSuccessful, List<Ride> ridesList) {

        if (!isSuccessful) {
            return;
        }

        for (Ride ride : ridesList) {
            rides.add(ride);
        }

        final int lastIndex = rides.size() - 1;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ridesAdapter.notifyItemRangeInserted(0, lastIndex);
            }
        });
    }
}
