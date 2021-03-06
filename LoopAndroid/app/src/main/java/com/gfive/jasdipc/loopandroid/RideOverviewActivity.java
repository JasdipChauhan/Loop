package com.gfive.jasdipc.loopandroid;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Fragments.RideOverviews.ExistingRideFragment;
import com.gfive.jasdipc.loopandroid.Fragments.RideOverviews.NewRideFragment;
import com.gfive.jasdipc.loopandroid.Interfaces.OnSpinnerSelection;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

    //Mark: Activity delegate methods

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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Mark: Map methods

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        plotMap();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onPickupSelected(String location) {
        pickup = location + ", Ontario, Canada";
        mMap.clear();
        plotMap();
    }

    @Override
    public void onDropoffSelected(String location) {
        dropoff = location + ", Ontario, Canada";
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

            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

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
            final LatLngBounds bounds = builder.build();

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Mark: EXISTING RIDE FRAGMENT interactions

    public void reserveButtonClicked (View view) {
        existingRideFragment.reserveRide(view);
    }

    public void riderListAction (View view) {
        existingRideFragment.showRiders(view);
    }

    public void messageButtonClicked (View view) {
        existingRideFragment.messageDriver(view);
    }

    public void deleteRideClicked (View view) {
        existingRideFragment.deleteRide(view);
    }

    //Mark: NEW RIDE FRAGMENT interactions

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

}
