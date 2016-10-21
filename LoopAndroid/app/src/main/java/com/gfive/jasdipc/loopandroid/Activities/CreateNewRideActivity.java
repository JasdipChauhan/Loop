package com.gfive.jasdipc.loopandroid.Activities;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Fragments.DateDialog;
import com.gfive.jasdipc.loopandroid.Fragments.TimeDialog;
import com.gfive.jasdipc.loopandroid.R;


public class CreateNewRideActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ride);

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

    }
}
