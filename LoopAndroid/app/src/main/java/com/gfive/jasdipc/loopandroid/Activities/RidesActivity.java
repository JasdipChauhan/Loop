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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Managers.RidesManager;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Interfaces.ParseCallback;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideDetailActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RidesActivity extends AppCompatActivity {

    private RecyclerView ridesRecyclerView;
    private FloatingActionButton addRideFAB;
    private FloatingActionButton refreshRideFAB;

    private FloatingActionButton logoutButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private RidesAdapter ridesAdapter;
    private List<User> users = new ArrayList<>();
    private List<Ride> rides = new ArrayList<>();
    private Context mContext;

    public static int RESERVE_RESULT = 99;
    private boolean isFirst = true;

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

        mContext = RidesActivity.this;

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(mContext);
        ridesRecyclerView.setLayoutManager(wCLLM);
        ridesAdapter = new RidesAdapter(rides, mContext);
        ridesRecyclerView.setAdapter(ridesAdapter);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESERVE_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                boolean didReserve = data.getBooleanExtra("didReserve", false);

                if (didReserve) {

                }
            }
        }
    }

    public void logoutAction(View view) {
        LoginManager.getInstance().logOut();
        Intent i = new Intent(RidesActivity.this, LoginActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_ride:

                startActivity(new Intent(RidesActivity.this, CreateNewRideActivity.class));
                break;

            case R.id.action_settings:

                handleLogout();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private void handleLogout() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(RidesActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

}
