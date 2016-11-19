package com.gfive.jasdipc.loopandroid.Fragments.RideOverviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Fragments.Dialogs.DateDialog;
import com.gfive.jasdipc.loopandroid.Fragments.Dialogs.TimeDialog;
import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;
import com.gfive.jasdipc.loopandroid.Interfaces.OnSpinnerSelection;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerPassback;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.gfive.jasdipc.loopandroid.R;

import org.json.JSONObject;

public class NewRideFragment extends Fragment implements AdapterView.OnItemSelectedListener, ServerPassback {

    private RelativeLayout dateContainer;
    private RelativeLayout timeContainer;
    private RelativeLayout pickupContainer;
    private RelativeLayout dropoffContainer;
    private RelativeLayout carContainer;
    private RelativeLayout priceContainer;

    private ScrollView scrollView;
    private TextView dateTV;
    private TextView timeTV;
    private Spinner pickupSpinner;
    private Spinner dropoffSpinner;
    private EditText ridePrice;
    private EditText pickupDescription;
    private EditText dropoffDescription;
    private EditText rideCar;
    private Button createButton;
    private TextView validatorTV;

    private ImageView[] riders;
    private ImageView riderIMG1;
    private ImageView riderIMG2;
    private ImageView riderIMG3;
    private ImageView riderIMG4;
    private ImageView riderIMG5;
    private ImageView riderIMG6;

    private String rideDateJSON;
    private String rideTimeJSON;
    private String pickupString;
    private String dropoffString;
    private String rideCarJSON;
    private double ridePriceJSON;
    private String pickupDesJSON;
    private String dropoffDesJSON;
    private int rideCapacity = 0;

    private ProgressDialog uploadProgress;

    private OnSpinnerSelection spinnerCallback;
    private static final String SPINNER_CALLBACK_PARAM = "SPINNER_CALLBACK";

    public NewRideFragment() {
        // Required empty public constructor
    }

    public static NewRideFragment newInstance() {
        NewRideFragment fragment = new NewRideFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_ride, container, false);

        dateContainer = (RelativeLayout) view.findViewById(R.id.date_container);
        timeContainer = (RelativeLayout) view.findViewById(R.id.time_container);
        pickupContainer = (RelativeLayout) view.findViewById(R.id.pickup_container);
        dropoffContainer = (RelativeLayout) view.findViewById(R.id.dropoff_container);
        carContainer = (RelativeLayout) view.findViewById(R.id.car_container);
        priceContainer = (RelativeLayout) view.findViewById(R.id.price_container);

        scrollView = (ScrollView) view.findViewById(R.id.scroll_view);
        dateTV = (TextView) view.findViewById(R.id.edit_ride_date);
        timeTV = (TextView) view.findViewById(R.id.edit_ride_time);
        pickupSpinner = (Spinner) view.findViewById(R.id.pickup_spinner);
        dropoffSpinner = (Spinner) view.findViewById(R.id.dropoff_spinner);
        ridePrice = (EditText) view.findViewById(R.id.ride_price);
        pickupDescription = (EditText) view.findViewById(R.id.pickup_description);
        dropoffDescription = (EditText) view.findViewById(R.id.dropoff_description);
        rideCar = (EditText) view.findViewById(R.id.ride_car);
        createButton = (Button) view.findViewById(R.id.create_button);
        validatorTV = (TextView) view.findViewById(R.id.field_validator);

        riderIMG1 = (ImageView) view.findViewById(R.id.create_rider1);
        riderIMG2 = (ImageView) view.findViewById(R.id.create_rider2);
        riderIMG3 = (ImageView) view.findViewById(R.id.create_rider3);
        riderIMG4 = (ImageView) view.findViewById(R.id.create_rider4);
        riderIMG5 = (ImageView) view.findViewById(R.id.create_rider5);
        riderIMG6 = (ImageView) view.findViewById(R.id.create_rider6);

        ArrayAdapter<CharSequence> pickupAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.location_array, android.R.layout.simple_spinner_item);
        pickupAdapter.setDropDownViewResource(R.layout.location_spinner_dropdown_item);
        pickupSpinner.setAdapter(pickupAdapter);

        ArrayAdapter<CharSequence> dropoffAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.location_array, android.R.layout.simple_spinner_item);
        dropoffAdapter.setDropDownViewResource(R.layout.location_spinner_dropdown_item);
        dropoffSpinner.setAdapter(dropoffAdapter);

        pickupSpinner.setOnItemSelectedListener(this);
        dropoffSpinner.setOnItemSelectedListener(this);

        riders = new ImageView[]{riderIMG1, riderIMG2, riderIMG3, riderIMG4, riderIMG5, riderIMG6};

        dateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(dateTV);
                dateDialog.show(getActivity().getFragmentManager(), "DateDialog");
            }
        });

        timeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog timeDialog = new TimeDialog(timeTV);
                timeDialog.show(getActivity().getFragmentManager(), "TimeDialog");
            }
        });

        return view;
    }


    //TODO: MAKE THIS MORE EFFICIENT
    public void handlePassengerClick(int passengerIndex) {

        rideCapacity = passengerIndex;
        createButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.enabled_button_bg));

        for (ImageView riderIMG : riders) {
            riderIMG.setImageResource(R.drawable.create_seat_unfilled);
        }

        for (int i = 0; i < rideCapacity; i++) {
            riders[i].setImageResource(R.drawable.create_seat);
        }
    }

    public void handleDatePicked() {
        DateDialog dateDialog = new DateDialog(dateTV);
        dateDialog.show(getActivity().getFragmentManager(), "DateDialog");
    }

    public void createRideAction () {

        if (!checkFieldData()) {
            validatorTV.setVisibility(View.VISIBLE);
            scrollView.fullScroll(View.FOCUS_DOWN);
            return;
        }

        uploadProgress = new ProgressDialog(getActivity());

        JSONObject rideJSONMap = new JSONObject();
        UserProfile profile;

        try {

            profile = ProfileManager.getInstance().getUserProfile();

            rideJSONMap.put("name", profile.name);
            rideJSONMap.put("email", profile.email);
            rideJSONMap.put("phoneNumber", profile.phoneNumber);
            rideJSONMap.put("pickup", pickupString);
            rideJSONMap.put("dropoff", dropoffString);
            rideJSONMap.put("date", rideDateJSON);
            rideJSONMap.put("time", rideTimeJSON);
            rideJSONMap.put("seats", rideCapacity);
            rideJSONMap.put("car", rideCarJSON);
            rideJSONMap.put("price", ridePriceJSON);
            rideJSONMap.put("pickupDescription", pickupDesJSON);
            rideJSONMap.put("dropoffDescription", dropoffDesJSON);

            UserProfile userProfile = ProfileManager.getInstance().getUserProfile();

            BackendClient.getInstance().uploadRide(this, userProfile, rideJSONMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.pickup_spinner:
                pickupString = parent.getItemAtPosition(position).toString().trim();
                spinnerCallback.onPickupSelected(pickupString);
                break;

            case R.id.dropoff_spinner:
                dropoffString = parent.getItemAtPosition(position).toString().trim();
                spinnerCallback.onDropoffSelected(dropoffString);
                break;

            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSpinnerSelection) {
            spinnerCallback = (OnSpinnerSelection) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        spinnerCallback = null;
    }

    @Override
    public void response(boolean isSuccessful, String responseData) {
        if (!isSuccessful) {
            Log.e("ERROR", "UNSUCCESSFUL RIDE UPLOAD");
        }

        StorageManager.getInstance(getContext()).saveDriverRides(responseData);

        uploadProgress.dismiss();
        getActivity().finish();
    }

    // MARK helper functions

    private boolean checkFieldData() {

        rideDateJSON = dateTV.getText().toString().trim();
        if (rideDateJSON.equalsIgnoreCase(getString(R.string.date_edit_text))) {
            return false;
        }

        rideTimeJSON = timeTV.getText().toString().trim();
        if (rideTimeJSON.equalsIgnoreCase(getString(R.string.time_edit_text))) {
            return false;
        }

        if (pickupString.equalsIgnoreCase(dropoffString)) {
            return false;
        }

        rideCarJSON = rideCar.getText().toString().trim();
        if (TextUtils.isEmpty(rideCarJSON)) {
            return false;
        }

        try {
            String priceStr = ridePrice.getText().toString().trim();
            ridePriceJSON = Double.parseDouble(priceStr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        pickupDesJSON = pickupDescription.getText().toString().trim();
        if (TextUtils.isEmpty(pickupDesJSON)) {
            return false;
        }

        dropoffDesJSON = dropoffDescription.getText().toString().trim();
        if (TextUtils.isEmpty(dropoffDesJSON)) {
            return false;
        }

        if (rideCapacity < 1 || rideCapacity > 6) {
            return false;
        }

        return true;
    }
}
