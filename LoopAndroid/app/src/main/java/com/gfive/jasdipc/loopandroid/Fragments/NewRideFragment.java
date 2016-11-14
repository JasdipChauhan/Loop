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

import com.gfive.jasdipc.loopandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewRideFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRideFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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

    public void handlePassengerClick(int passengerIndex) {
        for (int i = 0; i < passengerIndex; i++) {
            riders[i].setBackgroundResource(R.drawable.create_seat);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
