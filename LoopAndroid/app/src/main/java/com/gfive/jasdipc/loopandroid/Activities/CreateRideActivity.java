package com.gfive.jasdipc.loopandroid.Activities;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Fragments.DateDialog;
import com.gfive.jasdipc.loopandroid.Fragments.TimeDialog;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.gfive.jasdipc.loopandroid.R;

import org.json.JSONException;
import org.json.JSONObject;


public class CreateRideActivity extends AppCompatActivity implements ServerResponse {

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText email;
    private TextInputEditText phoneNumber;
    private TextInputEditText pickup;
    private TextInputEditText dropoff;
    private TextView datePickerTV;
    private TextView timePickerTV;
    private TextInputEditText seats;
    private TextInputEditText price;
    private Button createRideButton;

    private ProgressDialog mProgress;

    private String firstNameStr;
    private String lastNameStr;
    private String emailStr;
    private String phoneNumberStr;
    private String pickupStr;
    private String dropoffStr;
    private String dateStr;
    private String timeStr;
    private int seatsInt;
    private double priceDouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        init();
    }

    //actions
    public void timePickedAction (View view) {
        DialogFragment timeFragment = new TimeDialog(timePickerTV);
        timeFragment.show(getFragmentManager(), "TimeDialog");
    }

    public void datePickedAction (View view) {
        DialogFragment dateFragment = new DateDialog(datePickerTV);
        dateFragment.show(getFragmentManager(), "DateDialog");
    }

    public void createRideAction(View view) {

        boolean isSuccessful = getFieldData();

        if (!isSuccessful) {
            return;
        }

        mProgress = new ProgressDialog(this);

        JSONObject rideJSONMap = new JSONObject();
        UserProfile profile;
        try {

            profile = ProfileManager.getInstance().getUserProfile();

            rideJSONMap.put("name", profile.name);
            rideJSONMap.put("email", emailStr);
            rideJSONMap.put("phoneNumber", phoneNumberStr);
            rideJSONMap.put("pickup", pickupStr);
            rideJSONMap.put("dropoff", dropoffStr);
            rideJSONMap.put("date", dateStr);
            rideJSONMap.put("time", timeStr);
            rideJSONMap.put("seats", seatsInt);
            rideJSONMap.put("price", priceDouble);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("POST JSON", "COULD NOT INFLATE JSON");
            return;
        }

        BackendClient.getInstance().uploadRide(this, profile, rideJSONMap);

    }

    //HELPER FUNCTIONS
    private void init() {
        pickup = (TextInputEditText) findViewById(R.id.pickup_input);
        dropoff = (TextInputEditText) findViewById(R.id.dropoff_input);
        datePickerTV = (TextView) findViewById(R.id.datePickerTV);
        timePickerTV = (TextView) findViewById(R.id.timePickerTV);
        seats = (TextInputEditText) findViewById(R.id.seats_available_input);
        price = (TextInputEditText) findViewById(R.id.price_input);
        createRideButton = (Button) findViewById(R.id.create_ride_button);
    }

    private boolean getFieldData() {

        try {
            emailStr = email.getText().toString().trim();
            phoneNumberStr = phoneNumber.getText().toString().trim();
            pickupStr = pickup.getText().toString().trim();
            dropoffStr = dropoff.getText().toString().trim();
            dateStr = datePickerTV.getText().toString().trim();
            timeStr = timePickerTV.getText().toString().trim();
            seatsInt = Integer.parseInt(seats.getText().toString().trim());
            priceDouble = Double.parseDouble(price.getText().toString().trim());

        } catch (Exception e) {
            Snackbar.make(findViewById(R.id.create_ride_button), "Please fill out all fields...",
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(emailStr) ||
                TextUtils.isEmpty(phoneNumberStr) ||
                TextUtils.isEmpty(pickupStr) ||
                TextUtils.isEmpty(dropoffStr) ||
                TextUtils.isEmpty(dateStr) ||
                TextUtils.isEmpty(timeStr) ||
                seats == null) {

            Snackbar.make(findViewById(R.id.create_ride_button), "Please fill out all fields...",
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void response(boolean isSuccessful) {
        if (isSuccessful) {
            mProgress.dismiss();
            finish();
        } else {
            Snackbar.make(findViewById(R.id.create_ride_button), "Error creating ride, try again later...", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private JSONObject forTesting() {

        JSONObject rideJSONMap = new JSONObject();
        try {
            rideJSONMap.put("driver_name", "andorid" + " " + "lastnameandroid");
            rideJSONMap.put("driver_email", "android@gmail.com");
            rideJSONMap.put("driver_phone_number", "6475462930");
            rideJSONMap.put("pickup", "milton");
            rideJSONMap.put("dropoff", "kingston");
            rideJSONMap.put("date", "2016-05-06");
            rideJSONMap.put("time", "8:30");
            rideJSONMap.put("seats_left", 5);
            rideJSONMap.put("price", 3.5);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("POST JSON", "COULD NOT INFLATE JSON");
        }

        return rideJSONMap;
    }
}
