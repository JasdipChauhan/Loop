package com.gfive.jasdipc.loopandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Helpers.DateFormatter;
import com.gfive.jasdipc.loopandroid.Models.FirebaseRide;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RideDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RideDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RideDetailFragment extends Fragment {

    private static final String RIDE_PARAM = "RIDE_KEY";
    private FirebaseRide mRide;

    private OnFragmentInteractionListener mListener;

    private TextView rideDriversName;
    private TextView rideDate;
    private TextView ridePickup;
    private TextView rideDropoff;
    private TextView rideSeatsLeft;
    private TextView rideCost;
    private Button reserveButton;

    public RideDetailFragment() {
        // Required empty public constructor
    }

    public static RideDetailFragment newInstance(FirebaseRide mRide) {
        RideDetailFragment fragment = new RideDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RIDE_PARAM, mRide);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRide = getArguments().getParcelable(RIDE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.ride_detail_fragment, container, false);

        reserveButton = (Button) view.findViewById(R.id.reserve_button);
        rideDriversName = (TextView) view.findViewById(R.id.ride_drivers_name);
        rideDate = (TextView) view.findViewById(R.id.ride_date);
        ridePickup = (TextView) view.findViewById(R.id.ride_pickup);
        rideDropoff = (TextView) view.findViewById(R.id.ride_dropoff);
        rideSeatsLeft =(TextView) view.findViewById(R.id.ride_seats_left);
        rideCost = (TextView) view.findViewById(R.id.ride_cost);

        rideDriversName.setText(mRide.getDriver().getName());
        rideDate.setText(mRide.getDate());
        ridePickup.setText(mRide.getPickup());
        rideDropoff.setText(mRide.getDropoff());
        rideSeatsLeft.setText(Integer.toString(mRide.getSeatsLeft()));
        rideCost.setText(Double.toString(mRide.getPrice()));

        return view;
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
