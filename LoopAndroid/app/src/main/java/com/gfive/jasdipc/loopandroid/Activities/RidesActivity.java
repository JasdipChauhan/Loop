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
import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Models.FirebaseRide;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideDetailActivity;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RidesActivity extends AppCompatActivity {

    private RecyclerView mRidesRecyclerView;
    private DatabaseReference mDatabaseReference;

    private Context mContext;

    public static int RESERVE_RESULT = 99;

    private RidesAdapter ridesAdapter;
    private FirebaseRecyclerAdapter<FirebaseRide, RidesViewHolder> firebaseAdapter;

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

        mRidesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                mContext, mRidesRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        if (firebaseAdapter == null) {
                            return;
                        }

                        String rideKey = firebaseAdapter.getRef(position).getKey();

                        Intent rideDetailIntent = new Intent(mContext, RideDetailActivity.class);
                        rideDetailIntent.putExtra("ride", firebaseAdapter.getItem(position));
                        rideDetailIntent.putExtra("rideKey", rideKey);
                        startActivityForResult(rideDetailIntent, RESERVE_RESULT);
                        Log.i("POSITION", position + "");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

    }

    @Override
    protected void onStart() {
        super.onStart();

        ridesAdapter = RidesAdapter.getInstance(RidesActivity.this, mDatabaseReference);
        firebaseAdapter = ridesAdapter.getFirebaseRecyclerAdapter();
        mRidesRecyclerView.setAdapter(firebaseAdapter);

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

            case R.id.logout_menu:

                handleLogout();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private void handleLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(RidesActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

}
