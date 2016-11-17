package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.Adapters.FragmentAdapter;
import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Fragments.AllRidesFragment;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideOverviewActivity;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class RidesActivity extends AppCompatActivity implements AllRidesFragment.OnFragmentInteractionListener  {

    private RecyclerView mRidesRecyclerView;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mLocalDatabase;

    private Context mContext;

    public static int RESERVE_RESULT = 99;

    private RidesAdapter ridesAdapter;
    private FirebaseRecyclerAdapter<LoopRide, RidesViewHolder> firebaseAdapter;
    private boolean isMyRides = false;




    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    //Android Lifecycle Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        pager = (ViewPager) findViewById(R.id.view_pager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(fragmentAdapter);
//        mContext = RidesActivity.this;
//
//        mRidesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
//        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(mContext);
//        mRidesRecyclerView.setLayoutManager(wCLLM);
//        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Ride");
//
//        mRidesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
//                mContext, mRidesRecyclerView,
//                new RecyclerItemClickListener.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                        if (firebaseAdapter == null) {
//                            return;
//                        }
//
//                        String rideKey = firebaseAdapter.getRef(position).getKey();
//
//                        Intent rideDetailIntent = new Intent(mContext, RideOverviewActivity.class);
//                        rideDetailIntent.putExtra("existingRide", true);
//                        rideDetailIntent.putExtra("ride", firebaseAdapter.getItem(position));
//                        rideDetailIntent.putExtra("rideKey", rideKey);
//                        startActivity(rideDetailIntent);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//
//                    }
//                }));

    }


    private void handleLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(RidesActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    private void setAdapter() {

        if (isMyRides) {

            Set<String> savedRides = StorageManager.getInstance(RidesActivity.this).getSavedRides();

            ridesAdapter = RidesAdapter.getInstance(RidesActivity.this, mFirebaseDatabase);
            firebaseAdapter = ridesAdapter.getLocalRecyclerAdapter(savedRides);
            //mRidesRecyclerView.swapAdapter(firebaseAdapter, false);
            mRidesRecyclerView.setAdapter(firebaseAdapter);
            firebaseAdapter.notifyDataSetChanged();

        } else {
            ridesAdapter = RidesAdapter.getInstance(RidesActivity.this, mFirebaseDatabase);
            firebaseAdapter = ridesAdapter.getFirebaseRecyclerAdapter();
            //mRidesRecyclerView.swapAdapter(firebaseAdapter, false);
            mRidesRecyclerView.setAdapter(firebaseAdapter);
            firebaseAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //setAdapter();
    }

    @Override
    protected void onResume() {
        Log.i("onResume", "CALLED");
        super.onResume();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == RESERVE_RESULT) {
//            if (resultCode == Activity.RESULT_OK) {
//                boolean didReserve = data.getBooleanExtra("didReserve", false);
//
//                if (didReserve) {
//
//                }
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_ride:


                Intent rideDetailIntent = new Intent(mContext, RideOverviewActivity.class);
                rideDetailIntent.putExtra("existingRide", false);
                startActivity(rideDetailIntent);

                break;

            case R.id.logout_menu:

                handleLogout();
                break;

            case R.id.toggle_my_rides:

                if (item.isChecked()) {
                    isMyRides = true;
                    item.setChecked(true);
                } else {
                    isMyRides = false;
                    item.setChecked(false);
                }

                item.setChecked(!item.isChecked());
                isMyRides = item.isChecked();
                setAdapter();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
