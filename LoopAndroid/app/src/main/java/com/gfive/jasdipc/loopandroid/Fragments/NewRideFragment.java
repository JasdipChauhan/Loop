package com.gfive.jasdipc.loopandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Interfaces.OnSpinnerSelection;
import com.gfive.jasdipc.loopandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewRideFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRideFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private RelativeLayout dateContainer;
    private RelativeLayout timeContainer;
    private RelativeLayout pickupContainer;
    private RelativeLayout dropoffContainer;
    private RelativeLayout carContainer;
    private RelativeLayout priceContainer;

    private TextView dateTV;
    private TextView timeTV;
    private Spinner pickupSpinner;
    private Spinner dropoffSpinner;

    private ImageView[] riders;
    private ImageView riderIMG1;
    private ImageView riderIMG2;
    private ImageView riderIMG3;
    private ImageView riderIMG4;
    private ImageView riderIMG5;
    private ImageView riderIMG6;

    private String pickupString;
    private String dropoffString;
    private int passengerIndex = 0;

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

        dateTV = (TextView) view.findViewById(R.id.edit_ride_date);
        timeTV = (TextView) view.findViewById(R.id.edit_ride_time);
        pickupSpinner = (Spinner) view.findViewById(R.id.pickup_spinner);
        dropoffSpinner = (Spinner) view.findViewById(R.id.dropoff_spinner);

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

        this.passengerIndex = passengerIndex;

        for (ImageView riderIMG : riders) {
            riderIMG.setImageResource(R.drawable.create_seat_unfilled);
        }

        for (int i = 0; i < passengerIndex; i++) {
            riders[i].setImageResource(R.drawable.create_seat);
        }
    }

    public void handleDatePicked() {
        DateDialog dateDialog = new DateDialog(dateTV);
        dateDialog.show(getActivity().getFragmentManager(), "DateDialog");
    }

    public void createRideAction () {
        pickupString = pickupSpinner.getSelectedItem().toString().concat(", Ontario, Canada");
        dropoffString = dropoffSpinner.getSelectedItem().toString().concat(", Ontario, Canada");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        pickupString = pickupSpinner.getItemAtPosition(position).toString().concat(", Ontario, Canada");
        dropoffString = dropoffSpinner.getItemAtPosition(position).toString().concat(", Ontario, Canada");
        spinnerCallback.onPickupSelected(pickupString);
        spinnerCallback.onDropoffSelected(dropoffString);

        switch (view.getId()) {

            case R.id.pickup_spinner:
                pickupString = parent.getItemAtPosition(position).toString().concat(", Ontario, Canada");
                spinnerCallback.onPickupSelected(pickupString);
                break;

            case R.id.dropoff_spinner:
                dropoffString = parent.getItemAtPosition(position).toString().concat(", Ontario, Canada");
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
}
