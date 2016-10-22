package com.gfive.jasdipc.loopandroid;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.gfive.jasdipc.loopandroid.Fragments.RideDetailFragment;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RideDetailActivity extends FragmentActivity implements OnMapReadyCallback, RideDetailFragment.OnFragmentInteractionListener{

    private GoogleMap mMap;
    private Ride ride;
    private RideDetailFragment rideDetailFragment;

    private String pickup;
    private String dropoff;

    private final int MAP_PADDING = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Intent intent = getIntent();
        ride = intent.getParcelableExtra("ride");
        pickup = ride.getPickup();
        dropoff = ride.getDropoff();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        rideDetailFragment = RideDetailFragment.newInstance(ride);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_frame_layout, rideDetailFragment);
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(RideDetailActivity.this);
        try {
            List<Address> pickupAddressList = geocoder.getFromLocationName(pickup, 1);
            List<Address> dropoffAddressList = geocoder.getFromLocationName(dropoff, 1);

            Address pickupAddress = pickupAddressList.get(0);
            Address dropoffAddress = dropoffAddressList.get(0);

            LatLng pickupCoordinates = new LatLng(pickupAddress.getLatitude(), pickupAddress.getLongitude());
            LatLng dropoffCoordinates = new LatLng(dropoffAddress.getLatitude(), dropoffAddress.getLongitude());

            Marker pickupMarker = mMap.addMarker(new MarkerOptions().position(pickupCoordinates).title("Pickup"));
            Marker dropoffMarker = mMap.addMarker(new MarkerOptions().position(dropoffCoordinates).title("Dropoff"));

            List<Marker> markers = new ArrayList<>();
            markers.add(pickupMarker);
            markers.add(dropoffMarker);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("BUTTON CLICKED", "GOOOOOOD");
    }
}
