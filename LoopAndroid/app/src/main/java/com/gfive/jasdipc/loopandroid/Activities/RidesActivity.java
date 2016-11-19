package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.Adapters.PagerAdapter;
import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Fragments.Tabs.AllRidesFragment;
import com.gfive.jasdipc.loopandroid.Fragments.Tabs.MyRidesFragment;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideOverviewActivity;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Set;

public class RidesActivity extends AppCompatActivity implements AllRidesFragment.OnFragmentInteractionListener, MyRidesFragment.OnFragmentInteractionListener {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private TabLayout tabLayout;
    //Android Lifecycle Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        pager = (ViewPager) findViewById(R.id.rides_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.rides_tab_layout);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(PagerAdapter.ALL_RIDES).select();

    }

    private void handleLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(RidesActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("onResume", "CALLED");
        super.onResume();
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
                Intent rideDetailIntent = new Intent(RidesActivity.this, RideOverviewActivity.class);
                rideDetailIntent.putExtra("existingRide", false);
                startActivity(rideDetailIntent);

                break;

            case R.id.logout_menu:

                handleLogout();
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
