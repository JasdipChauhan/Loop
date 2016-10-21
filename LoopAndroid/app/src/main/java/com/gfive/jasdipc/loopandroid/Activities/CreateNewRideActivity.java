package com.gfive.jasdipc.loopandroid.Activities;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Fragments.DateDialog;
import com.gfive.jasdipc.loopandroid.Fragments.TimeDialog;
import com.gfive.jasdipc.loopandroid.R;


public class CreateNewRideActivity extends AppCompatActivity {

    private DateDialog dateDialog;
    private TextView datePickerTV;
    private TextView timePickerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ride);

        datePickerTV = (TextView) findViewById(R.id.datePickerTV);
        timePickerTV = (TextView) findViewById(R.id.timePickerTV);
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
}
