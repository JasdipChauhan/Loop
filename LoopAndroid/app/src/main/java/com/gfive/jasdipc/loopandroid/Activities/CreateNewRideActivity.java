package com.gfive.jasdipc.loopandroid.Activities;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Clients.APIClient;
import com.gfive.jasdipc.loopandroid.Fragments.DateDialog;
import com.gfive.jasdipc.loopandroid.Fragments.TimeDialog;
import com.gfive.jasdipc.loopandroid.Interfaces.OnServerResponse;
import com.gfive.jasdipc.loopandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class CreateNewRideActivity extends AppCompatActivity implements OnServerResponse {

    private APIClient apiClient;

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
        setContentView(R.layout.activity_create_new_ride);
        init();

        apiClient = APIClient.getInstance();
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
        getFieldData();

        JSONObject rideJSONMap = new JSONObject();
        try {
            rideJSONMap.put("driver_name", firstNameStr + " " + lastNameStr);
            rideJSONMap.put("driver_email", emailStr);
            rideJSONMap.put("driver_phone_number", phoneNumberStr);
            rideJSONMap.put("pickup", pickupStr);
            rideJSONMap.put("dropoff", dropoffStr);
            rideJSONMap.put("date", dateStr);
            rideJSONMap.put("time", timeStr);
            rideJSONMap.put("seats_left", seatsInt);
            rideJSONMap.put("price", priceDouble);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("POST JSON", "COULD NOT INFLATE JSON");
        }

        apiClient.postRide(this, rideJSONMap);

    }


    @Override
    public void serverCallback(Boolean isSuccessful, Response serverResponse) {
        if (isSuccessful) {
            finish();
        }
    }

    //HELPER FUNCTIONS
    private void init() {
        firstName = (TextInputEditText) findViewById(R.id.first_name_input);
        lastName = (TextInputEditText) findViewById(R.id.last_name_input);
        email = (TextInputEditText) findViewById(R.id.driver_email_input);
        phoneNumber = (TextInputEditText) findViewById(R.id.driver_phone_input);
        pickup = (TextInputEditText) findViewById(R.id.pickup_input);
        dropoff = (TextInputEditText) findViewById(R.id.dropoff_input);
        datePickerTV = (TextView) findViewById(R.id.datePickerTV);
        timePickerTV = (TextView) findViewById(R.id.timePickerTV);
        seats = (TextInputEditText) findViewById(R.id.seats_available_input);
        price = (TextInputEditText) findViewById(R.id.price_input);
        createRideButton = (Button) findViewById(R.id.create_ride_button);
    }

    //TODO: input validation
    private void getFieldData() {
        firstNameStr = firstName.getText().toString();
        lastNameStr = lastName.getText().toString();
        emailStr = email.getText().toString();
        phoneNumberStr = phoneNumber.getText().toString();
        pickupStr = pickup.getText().toString();
        dropoffStr = dropoff.getText().toString();
        dateStr = datePickerTV.getText().toString();
        timeStr = timePickerTV.getText().toString();
        seatsInt = Integer.parseInt(seats.getText().toString());
        priceDouble = Double.parseDouble(price.getText().toString());
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
