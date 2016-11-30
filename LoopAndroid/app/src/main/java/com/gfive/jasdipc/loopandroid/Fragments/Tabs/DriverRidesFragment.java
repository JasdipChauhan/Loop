package com.gfive.jasdipc.loopandroid.Fragments.Tabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Helpers.RecyclerItemClickListener;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.Managers.StorageManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideOverviewActivity;
import com.gfive.jasdipc.loopandroid.ViewHolders.DriverViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class DriverRidesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private DatabaseReference mDatabaseReference;
    private RecyclerView mDriverRidesView;
    private Set<String> mSavedRides;
    private RidesAdapter mRidesAdapter;
    private FirebaseRecyclerAdapter<LoopRide, DriverViewHolder> driverAdapter;

    public DriverRidesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DriverRidesFragment newInstance() {

        DriverRidesFragment fragment = new DriverRidesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ride");
        mSavedRides = StorageManager.getInstance(getContext()).getDriverRides();
        mRidesAdapter = new RidesAdapter(getContext(), mDatabaseReference);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_driver_rides, container, false);

        mDriverRidesView = (RecyclerView) view.findViewById(R.id.driver_rides_recycler_view);

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(getContext());
        driverAdapter = mRidesAdapter.getDriverRidesAdapter(StorageManager.getInstance(getContext()).getDriverRides());

        mDriverRidesView.setLayoutManager(wCLLM);
        mDriverRidesView.setAdapter(driverAdapter);

        mDriverRidesView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mDriverRidesView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String rideKey = driverAdapter.getRef(position).getKey();

                Intent rideDetailIntent = new Intent(getContext(), RideOverviewActivity.class);
                rideDetailIntent.putExtra("existingRide", true);
                rideDetailIntent.putExtra("ride", driverAdapter.getItem(position));
                rideDetailIntent.putExtra("rideKey", rideKey);
                startActivity(rideDetailIntent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

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
