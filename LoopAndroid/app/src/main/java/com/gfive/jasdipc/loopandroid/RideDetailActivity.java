package com.gfive.jasdipc.loopandroid;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Fragments.RideDetailFragment;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.FirebaseRide;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
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

public class RideDetailActivity extends AppCompatActivity implements OnMapReadyCallback, RideDetailFragment.OnFragmentInteractionListener {

    private GoogleMap mMap;
    private FirebaseRide ride;
    private String rideKey;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        ride = intent.getParcelableExtra("ride");
        rideKey = intent.getStringExtra("rideKey");

        pickup = ride.getPickup();
        dropoff = ride.getDropoff();
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
        Log.i("button clicked", "button clicked");

        if (!TextUtils.isEmpty(rideKey)) {

            UserProfile currentUser = ProfileManager.getInstance().getUserProfile();

            BackendClient.getInstance().reserveRide(rideKey, currentUser.id, new ServerResponse() {
                        @Override
                        public void response(boolean isSuccessful) {

                            if (isSuccessful) {
                                StorageManager.getInstance(RideDetailActivity.this)
                                        .saveRide(rideKey);
                                finish();

                            } else {
                                Toast.makeText(RideDetailActivity.this, "Ride is full", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
//
//        int rideID = ride.getId();
//
        try {
//            updateRideObject.put("driver_name", ride.getDriver().getName());
//            updateRideObject.put("driver_email", ride.getDriver().getEmail());
//            updateRideObject.put("driver_phone_number", ride.getDriver().getPhoneNumber());
//            updateRideObject.put("pickup", ride.getPickup());
//            updateRideObject.put("dropoff", ride.getDropoff());
//            updateRideObject.put("date", Formatter.formatToYYYYMMDD(ride.getDate()));
//            updateRideObject.put("time", ride.getTime());
//            updateRideObject.put("seats_left", ride.getPassengers() - 1);
//            updateRideObject.put("price", ride.getCost());

            //JSONObject testing = new JSONObject("{\"driver_name\":\"Jasdip Chauhan\", \"driver_email\":\"jasdip.chauhan@gmail.com\", \"driver_phone_number\":\"6475273055\", \"pickup\": \"Markham\", \"dropoff\": \"Waterloo\",\"date\": \"2016-10-14\", \"time\": \"7:30\", \"seats_left\": 1, \"price\": \"10.50\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }

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
