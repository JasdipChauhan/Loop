package com.gfive.jasdipc.loopandroid.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.facebook.login.LoginManager;
import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Managers.RidesManager;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Interfaces.ParseCallback;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RidesActivity extends AppCompatActivity implements ParseCallback {

    private RecyclerView ridesRecyclerView;
    private FloatingActionButton addRideFAB;
    private FloatingActionButton refreshRideFAB;

    private FloatingActionButton logoutButton;

    private RidesAdapter ridesAdapter;
    private List<User> users = new ArrayList<>();
    private List<Ride> rides = new ArrayList<>();
    private Context mContext;

    private RidesManager ridesManager;
    private Snackbar statusSnackbar;

    public static final int ON_START_UP = 1;
    public static final int ON_RESERVE_RIDE = 2;
    public static final int ON_RETRIEVE_RIDES = 3;
    public static final int ON_GETTING_RIDES = 4;
    public static final int ON_SERVER_ERROR = 5;
    public static final int ON_RESERVE_RIDE_ERROR = 6;
    public static final int ON_REENTER = 7;

    public static int RESERVE_RESULT = 99;
    private boolean isFirst = true;

    @Override
    protected void onResume() {
        Log.i("onResume", "CALLED");
        super.onResume();

        refreshList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        ridesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        addRideFAB = (FloatingActionButton) findViewById(R.id.add_ride_FAB);
        refreshRideFAB = (FloatingActionButton) findViewById(R.id.refresh_ride_FAB);
        logoutButton = (FloatingActionButton) findViewById(R.id.logout_FAB);

        ridesManager = new RidesManager();
        mContext = RidesActivity.this;

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(mContext);
        ridesRecyclerView.setLayoutManager(wCLLM);
        ridesAdapter = new RidesAdapter(rides, mContext);
        ridesRecyclerView.setAdapter(ridesAdapter);

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
                refreshList();
            }
        });

        ridesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                mContext, ridesRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent rideDetailIntent = new Intent(mContext, RideDetailActivity.class);
                        rideDetailIntent.putExtra("ride", (Parcelable) rides.get(position));
                        startActivityForResult(rideDetailIntent, RESERVE_RESULT);
                        Log.i("POSITION", position + "");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

    }

    @Override
    public void retrieveRides(final boolean isSuccessful, List<Ride> ridesList) {

        if (!isSuccessful) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    statusSnackbar.setText(getSnackbarMessage(ON_SERVER_ERROR));
                    statusSnackbar.show();
                }
            });
            return;
        }

        rides.clear();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESERVE_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                boolean didReserve = data.getBooleanExtra("didReserve", false);

                if (didReserve) {
                    statusSnackbar = Snackbar.make(findViewById(R.id.rides_recycler_view), getSnackbarMessage(ON_RESERVE_RIDE),
                            Snackbar.LENGTH_SHORT);
                    statusSnackbar.show();
                }
            }
        }
    }

    public void logoutAction(View view) {
        LoginManager.getInstance().logOut();
        Intent i = new Intent(RidesActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void refreshList() {
        ridesManager.refreshRidesList(this);
    }

    public static String getSnackbarMessage(int statusCode) {

        String message = "";

        switch (statusCode) {
            case ON_START_UP:
                message = "Welcome to LOOP!";
                break;

            case ON_RESERVE_RIDE:
                message = "Successfully booked ride!";
                break;

            case ON_RETRIEVE_RIDES:
                message = "Successfully retrieved rides!";
                break;

            case ON_GETTING_RIDES:
                message = "Refreshing the list of rides...";
                break;

            case ON_SERVER_ERROR:
                message = "Error retrieving the list of rides...";
                break;

            case ON_REENTER:
                message = "";
            break;

            default:
        }

        return message;
    }


}
