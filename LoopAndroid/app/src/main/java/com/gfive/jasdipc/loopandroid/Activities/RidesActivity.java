package com.gfive.jasdipc.loopandroid.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Models.FirebaseRide;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RidesActivity extends AppCompatActivity {

    private RecyclerView mRidesRecyclerView;
    private DatabaseReference mDatabaseReference;

    private List<User> users = new ArrayList<>();
    private List<Ride> rides = new ArrayList<>();
    private Context mContext;

    public static int RESERVE_RESULT = 99;
    private boolean isFirst = true;

    //Android Lifecycle Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        mContext = RidesActivity.this;

        mRidesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(mContext);
        mRidesRecyclerView.setLayoutManager(wCLLM);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ride");

        //mRidesRecyclerView.setHasFixedSize(true);


        //ridesAdapter = new RidesAdapter(rides, mContext);
        //mRidesRecyclerView.setAdapter(ridesAdapter);

        mRidesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                mContext, mRidesRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

//                        Intent rideDetailIntent = new Intent(mContext, RideDetailActivity.class);
//                        rideDetailIntent.putExtra("ride", (Parcelable) rides.get(position));
//                        startActivityForResult(rideDetailIntent, RESERVE_RESULT);
//                        Log.i("POSITION", position + "");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<FirebaseRide, RidesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FirebaseRide, RidesViewHolder>(

                FirebaseRide.class,
                R.layout.ride_card,
                RidesViewHolder.class,
                mDatabaseReference
                ) {
            @Override
            protected void populateViewHolder(RidesViewHolder holder, FirebaseRide model, int position) {

                holder.usersName.setText(model.getDriver().getName());
                holder.date.setText(model.getDate());
                holder.pickup.setText(model.getPickup());
                holder.dropoff.setText(model.getDropoff());
                holder.pickupTime.setText(model.getTime());
                holder.passengers.setText(model.getSeatsLeft() + "");
                holder.cost.setText(model.getPrice() + "");
//

                //holder.userImage.setImageURI(ProfileManager.getInstance().getUserProfile().profilePictureURI);
                //runEnterAnimation(holder.itemView, position);
            }
        };

        mRidesRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onResume() {
        Log.i("onResume", "CALLED");
        super.onResume();
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
