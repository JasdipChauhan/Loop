package com.gfive.jasdipc.loopandroid.Fragments.RideOverviews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExistingRideFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExistingRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExistingRideFragment extends Fragment {

    private static final String RIDE_PARAM = "RIDE_KEY";
    private LoopRide mRide;

    private OnFragmentInteractionListener mListener;

    private TextView rideDate;
    private TextView rideTime;
    private TextView ridePickup;
    private TextView rideDropoff;
    private TextView rideCost;
    private TextView rideCar;
    private EditText pickupDescription;
    private EditText dropoffDescription;

    private ImageView[] riders;
    private ImageView riderIMG1;
    private ImageView riderIMG2;
    private ImageView riderIMG3;
    private ImageView riderIMG4;
    private ImageView riderIMG5;
    private ImageView riderIMG6;

    private Button reserveButton;

    public ExistingRideFragment() {
        // Required empty public constructor
    }

    public static ExistingRideFragment newInstance(LoopRide mRide) {
        ExistingRideFragment fragment = new ExistingRideFragment();
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

        final View view = inflater.inflate(R.layout.fragment_existing_ride, container, false);

        reserveButton = (Button) view.findViewById(R.id.reserve_button);
        rideDate = (TextView) view.findViewById(R.id.ride_date);
        rideTime = (TextView) view.findViewById(R.id.ride_time);
        ridePickup = (TextView) view.findViewById(R.id.ride_pickup);
        rideDropoff = (TextView) view.findViewById(R.id.ride_dropoff);
        rideCost = (TextView) view.findViewById(R.id.ride_price);
        rideCar = (TextView) view.findViewById(R.id.ride_car);
        pickupDescription = (EditText) view.findViewById(R.id.pickup_description);
        dropoffDescription = (EditText) view.findViewById(R.id.dropoff_description);

        riderIMG1 = (ImageView) view.findViewById(R.id.rider1);
        riderIMG2 = (ImageView) view.findViewById(R.id.rider2);
        riderIMG3 = (ImageView) view.findViewById(R.id.rider3);
        riderIMG4 = (ImageView) view.findViewById(R.id.rider4);
        riderIMG5 = (ImageView) view.findViewById(R.id.rider5);
        riderIMG6 = (ImageView) view.findViewById(R.id.rider6);
        riders = new ImageView[]{riderIMG1, riderIMG2, riderIMG3, riderIMG4, riderIMG5, riderIMG6};

        rideDate.setText(FormatHelper.getReadableDate(mRide.getDate()));
        rideTime.setText(FormatHelper.getReadableTime(mRide.getTime()));
        ridePickup.setText(mRide.getPickup());
        rideDropoff.setText(mRide.getDropoff());
        rideCost.setText(Double.toString(mRide.getPrice()));
        rideCar.setText(mRide.getCar());
        pickupDescription.setText(mRide.getPickupDescription());
        dropoffDescription.setText(mRide.getDropoffDescription());

        for (int i = 0; i < mRide.getSeatsSize(); i++) {

            if (i < mRide.getSeatsLeft()) {
                riders[i].setImageResource(R.drawable.create_seat_unfilled);
            } else {
                riders[i].setImageResource(R.drawable.create_seat);
            }

            riders[i].setVisibility(View.VISIBLE);
        }

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
