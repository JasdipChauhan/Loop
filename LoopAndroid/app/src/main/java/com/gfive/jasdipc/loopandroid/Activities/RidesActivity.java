package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Adapters.PagerAdapter;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Fragments.Tabs.AllRidesFragment;
import com.gfive.jasdipc.loopandroid.Fragments.Tabs.DriverRidesFragment;
import com.gfive.jasdipc.loopandroid.Fragments.Tabs.MyRidesFragment;
import com.gfive.jasdipc.loopandroid.LoginActivity;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideOverviewActivity;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RidesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AllRidesFragment.OnFragmentInteractionListener, MyRidesFragment.OnFragmentInteractionListener, DriverRidesFragment.OnFragmentInteractionListener {


    //MARK NAV BAR PROPERTIES
    private ImageView mNavProfile;
    private TextView mNavName;
    private TextView mNavPhoneNumber;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.username7);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        pager = (ViewPager) findViewById(R.id.rides_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.rides_tab_layout);
        mNavProfile = (ImageView) findViewById(R.id.nav_profile_picture);
        mNavName = (TextView) findViewById(R.id.nav_name);
        mNavPhoneNumber = (TextView) findViewById(R.id.nav_phone_number);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(PagerAdapter.ALL_RIDES).select();

        setupNav();
    }

    @Override
    protected void onStart() {
        super.onStart();

        BackendClient.getInstance().cleanDatabase();


    }

    //MARK: Nav bar methods

    public void signOutAction(View view) {
        handleLogout();
    }

    private void setupNav() {
        LoopUser user = ProfileManager.getInstance().getLoopUser();

        Picasso.with(RidesActivity.this).load(user.getPhoto()).into(mNavProfile);
        mNavName.setText(user.getName());
        mNavPhoneNumber.setText(user.getPhoneNumber());
    }

    //MARK: Activity methods

    private void handleLogout() {
        ProfileManager.getInstance().signOut();

        Intent intent = new Intent(RidesActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_ride:
                Intent rideDetailIntent = new Intent(RidesActivity.this, RideOverviewActivity.class);
                rideDetailIntent.putExtra("existingRide", false);
                startActivity(rideDetailIntent);

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}