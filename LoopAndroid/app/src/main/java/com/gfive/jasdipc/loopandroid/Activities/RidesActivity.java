package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Controllers.RidesController;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Interfaces.ParseCallback;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RidesActivity extends AppCompatActivity implements ParseCallback{

    private RecyclerView ridesRecyclerView;
    private FloatingActionButton addRideFAB;
    private FloatingActionButton refreshRideFAB;

    private RidesAdapter ridesAdapter;
    private List<User> users = new ArrayList<>();
    private List<Ride> rides = new ArrayList<>();
    private Context mContext;

    private RidesController ridesController;


    @Override
    protected void onResume() {
        Log.i("onResume", "CALLED");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        ridesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        addRideFAB = (FloatingActionButton) findViewById(R.id.add_ride_FAB);
        refreshRideFAB = (FloatingActionButton) findViewById(R.id.refresh_ride_FAB);

        mContext = RidesActivity.this;

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(mContext);
        ridesRecyclerView.setLayoutManager(wCLLM);

        ridesAdapter = new RidesAdapter(rides, mContext);
        ridesRecyclerView.setAdapter(ridesAdapter);

        ridesController = new RidesController();
        ridesController.refreshRidesList(this);

        addRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RidesActivity.this, CreateNewRideActivity.class);
                startActivity(i);
            }
        });

        refreshRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ridesAdapter.clear();
                ridesController.refreshRidesList(RidesActivity.this);
            }
        });

        ridesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                mContext, ridesRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                Ride selectedRide = rides.get(position);

                Intent rideDetailIntent = new Intent(mContext, RideDetailActivity.class);
                rideDetailIntent.putExtra("ride", (Parcelable) rides.get(position));
                startActivity(rideDetailIntent);
                Log.i("POSITION", position+"");
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

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
