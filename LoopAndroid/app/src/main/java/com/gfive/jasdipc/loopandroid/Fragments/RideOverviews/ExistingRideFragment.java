package com.gfive.jasdipc.loopandroid.Fragments.RideOverviews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Activities.RiderActivity;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideOverviewActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExistingRideFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExistingRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExistingRideFragment extends Fragment {

    private static final String RIDE_PARAM = "RIDE";
    private static final String RIDE_KEY_PARAM = "RIDE_KEY";
    private LoopRide mRide;
    private String mRideKey;

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

    public static ExistingRideFragment newInstance(LoopRide mRide, String mRideKey) {
        ExistingRideFragment fragment = new ExistingRideFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RIDE_PARAM, mRide);
        bundle.putString(RIDE_KEY_PARAM, mRideKey);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRide = getArguments().getParcelable(RIDE_PARAM);
            mRideKey = getArguments().getString(RIDE_KEY_PARAM);
        }

        setHasOptionsMenu(true);
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

        rideDate.setText(FormatHelper.getReadableDate(FormatHelper.toReadableFormat(mRide.getDate())));
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

        if (StorageManager.getInstance(getContext()).isAlreadySaved(mRideKey)) {
            reserveButton.setText("Booked");
            reserveButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.created_bg));
        }

        return view;
    }

    public void reserveRide(View view) {
        if (!TextUtils.isEmpty(mRideKey)) {

            LoopUser currentUser = ProfileManager.getInstance().getLoopUser();

            BackendClient.getInstance().reserveRide(mRideKey, currentUser, new ServerAction() {
                @Override
                public void response(boolean isSuccessful) {

                    if (isSuccessful) {

                        if (StorageManager.getInstance(getContext()).isAlreadySaved(mRideKey)) {
                            Snackbar.make(getView().findViewById(R.id.ride_overview_layout), R.string.already_booked_snackbar, Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        StorageManager.getInstance(getContext())
                                .saveRiderRides(mRideKey);

                        Snackbar.make(getView().findViewById(R.id.ride_overview_layout), R.string.book_ride_success, Snackbar.LENGTH_SHORT).show();

                    } else {
                        Snackbar.make(getView().findViewById(R.id.ride_overview_layout), R.string.book_ride_failure, Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    public void messageDriver(View view) {

        Uri smsURI = Uri.fromParts("sms", mRide.getDriver().getPhoneNumber(), null);
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, smsURI);

        startActivity(smsIntent);
    }

    public void showRiders(View view) {
        Intent toRiders = new Intent(getContext(), RiderActivity.class);
        toRiders.putExtra("RIDE_KEY", mRideKey);
        startActivity(toRiders);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_book_ride:
                reserveButton.callOnClick();
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.book_ride_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
