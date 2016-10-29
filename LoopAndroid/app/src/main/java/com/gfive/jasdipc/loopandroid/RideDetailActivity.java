package com.gfive.jasdipc.loopandroid;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Fragments.RideDetailFragment;
import com.gfive.jasdipc.loopandroid.Helpers.DateFormatter;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class RideDetailActivity extends FragmentActivity implements OnMapReadyCallback, RideDetailFragment.OnFragmentInteractionListener{

    private GoogleMap mMap;
    private Ride ride;
    private RideDetailFragment rideDetailFragment;
    private Snackbar snackbarStatus;
    private boolean didUserReserve = false;
    private Intent returnIntent;

    private String pickup;
    private String dropoff;

    private final int MAP_PADDING = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Intent intent = getIntent();
        ride = intent.getParcelableExtra("ride");
//        pickup = ride.getPickup();
//        dropoff = ride.getDropoff();
        returnIntent = new Intent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rideDetailFragment = RideDetailFragment.newInstance(ride);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_frame_layout, rideDetailFragment);
        transaction.commit();
    }

    public void reserveButtonClicked (View view) {
        Log.i("butotn clicked", "button clicked");
        JSONObject updateRideObject = new JSONObject();
//
//        int rideID = ride.getId();
//
        try {
//            updateRideObject.put("driver_name", ride.getDriver().getName());
//            updateRideObject.put("driver_email", ride.getDriver().getEmail());
//            updateRideObject.put("driver_phone_number", ride.getDriver().getPhoneNumber());
//            updateRideObject.put("pickup", ride.getPickup());
//            updateRideObject.put("dropoff", ride.getDropoff());
//            updateRideObject.put("date", DateFormatter.formatToYYYYMMDD(ride.getDate()));
//            updateRideObject.put("time", ride.getTime());
//            updateRideObject.put("seats_left", ride.getPassengers() - 1);
//            updateRideObject.put("price", ride.getCost());

            //JSONObject testing = new JSONObject("{\"driver_name\":\"Jasdip Chauhan\", \"driver_email\":\"jasdip.chauhan@gmail.com\", \"driver_phone_number\":\"6475273055\", \"pickup\": \"Markham\", \"dropoff\": \"Waterloo\",\"date\": \"2016-10-14\", \"time\": \"7:30\", \"seats_left\": 1, \"price\": \"10.50\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }

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
