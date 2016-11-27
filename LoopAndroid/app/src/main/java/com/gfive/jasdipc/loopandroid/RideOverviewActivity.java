package com.gfive.jasdipc.loopandroid;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Activities.RiderActivity;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Fragments.RideOverviews.ExistingRideFragment;
import com.gfive.jasdipc.loopandroid.Fragments.RideOverviews.NewRideFragment;
import com.gfive.jasdipc.loopandroid.Interfaces.OnSpinnerSelection;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class RideOverviewActivity extends AppCompatActivity implements OnMapReadyCallback, ExistingRideFragment.OnFragmentInteractionListener, OnSpinnerSelection {

    private GoogleMap mMap;
    private LoopRide ride;
    private String rideKey;

    private ExistingRideFragment existingRideFragment;
    private NewRideFragment newRideFragment;

    private String pickup;
    private String dropoff;
    private boolean isExistingRide;

    private Marker pickupMarker;
    private Marker dropoffMarker;

    private final int MAP_PADDING = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_overview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        isExistingRide = intent.getBooleanExtra("existingRide", false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ride = new LoopRide();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isExistingRide) {
            setTitle(getString(R.string.book_ride_title));
            ride = intent.getParcelableExtra("ride");
            rideKey = intent.getStringExtra("rideKey");

            pickup = ride.getPickup().concat(", Ontario, Canada");
            dropoff = ride.getDropoff().concat(", Ontario, Canada");

            existingRideFragment = ExistingRideFragment.newInstance(ride, rideKey);
            transaction.replace(R.id.ride_frame, existingRideFragment);
        } else {

            setTitle(getString(R.string.create_ride_title));
            newRideFragment = NewRideFragment.newInstance();
            transaction.replace(R.id.ride_frame, newRideFragment);

        }

        transaction.commit();
    }

    public void reserveButtonClicked (View view) {
        Log.i("button clicked", "button clicked");

        if (!TextUtils.isEmpty(rideKey)) {

            LoopUser currentUser = ProfileManager.getInstance().getLoopUser();

            BackendClient.getInstance().reserveRide(rideKey, currentUser, new ServerAction() {
                        @Override
                        public void response(boolean isSuccessful) {

                            if (isSuccessful) {

                                if (StorageManager.getInstance(RideOverviewActivity.this).isAlreadySaved(rideKey)) {
                                    Snackbar.make(findViewById(R.id.ride_overview_layout), R.string.already_booked_snackbar, Snackbar.LENGTH_SHORT).show();
                                    return;
                                }

                                StorageManager.getInstance(RideOverviewActivity.this)
                                        .saveRiderRides(rideKey);

                                Snackbar.make(findViewById(R.id.ride_overview_layout), R.string.book_ride_success, Snackbar.LENGTH_SHORT).show();


                            } else {
                                Snackbar.make(findViewById(R.id.ride_overview_layout), R.string.book_ride_failure, Snackbar.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }

    public void createButtonClicked (View view) {
        newRideFragment.createRideAction();
    }

    public void imageFactoryAction(View view) {

        int index;

        switch (view.getId()) {

            case R.id.create_rider1:
                index = 1;
                break;

            case R.id.create_rider2:
                index = 2;
                break;

            case R.id.create_rider3:
                index = 3;
                break;

            case R.id.create_rider4:
                index = 4;
                break;

            case R.id.create_rider5:
                index = 5;
                break;

            case R.id.create_rider6:
                index = 6;
                break;

            default:
                index = 0;

        }

        newRideFragment.handlePassengerClick(index);
    }

    public void dateClickedAction(View view) {
        newRideFragment.handleDatePicked();
    }

    public void messageButtonClicked (View view) {
        existingRideFragment.messageDriver(view);
    }

    public void riderListAction (View view) {
        Intent toRiders = new Intent(RideOverviewActivity.this, RiderActivity.class);
        toRiders.putExtra("RIDE_KEY", rideKey);
        startActivity(toRiders);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_ride_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        plotMap();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("BUTTON CLICKED", "GOOOOOOD");
    }

    @Override
    public void onPickupSelected(String location) {
        mMap.clear();
        plotMap();
    }

    @Override
    public void onDropoffSelected(String location) {
        mMap.clear();
        plotMap();
    }

    private void plotMap() {
        Geocoder geocoder = new Geocoder(RideOverviewActivity.this);

        try {
            List<Address> pickupAddressList = geocoder.getFromLocationName(pickup, 1);
            List<Address> dropoffAddressList = geocoder.getFromLocationName(dropoff, 1);

            Address pickupAddress = pickupAddressList.get(0);
            Address dropoffAddress = dropoffAddressList.get(0);

            LatLng pickupCoordinates = new LatLng(pickupAddress.getLatitude(), pickupAddress.getLongitude());
            LatLng dropoffCoordinates = new LatLng(dropoffAddress.getLatitude(), dropoffAddress.getLongitude());

            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.map_pin);

            pickupMarker = mMap.addMarker(new MarkerOptions()
                    .position(pickupCoordinates)
                    .title("Pickup")
                    .icon(markerIcon));

            dropoffMarker = mMap.addMarker(new MarkerOptions()
                    .position(dropoffCoordinates)
                    .title("Dropoff")
                    .icon(markerIcon));


            mMap.addPolyline(new PolylineOptions()
                    .add(pickupMarker.getPosition(), dropoffMarker.getPosition())
                    .width(5)
                    .color(ContextCompat.getColor(this, R.color.colorAccentShadow))
                    .geodesic(true));

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(pickupMarker.getPosition());
            builder.include(dropoffMarker.getPosition());
            LatLngBounds bounds = builder.build();

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
