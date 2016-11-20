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
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.RideOverviewActivity;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllRidesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllRidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllRidesFragment extends Fragment  {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mAllRidesView;
    private RidesAdapter mRidesAdapter;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<LoopRide, RidesViewHolder> firebaseRecyclerAdapter;

    public AllRidesFragment() {
        // Required empty public constructor
    }

    public static AllRidesFragment newInstance() {
        AllRidesFragment fragment = new AllRidesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ride");
        mRidesAdapter = new RidesAdapter(getContext(), mDatabaseReference);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_all_rides, container, false);

        mAllRidesView = (RecyclerView) view.findViewById(R.id.all_rides_recycler_view);

        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(getContext());

        mAllRidesView.setLayoutManager(wCLLM);
        firebaseRecyclerAdapter = mRidesAdapter.getFirebaseRecyclerAdapter();
        mAllRidesView.setAdapter(firebaseRecyclerAdapter);

        mAllRidesView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mAllRidesView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String rideKey = firebaseRecyclerAdapter.getRef(position).getKey();

                Intent rideDetailIntent = new Intent(getContext(), RideOverviewActivity.class);
                rideDetailIntent.putExtra("existingRide", true);
                rideDetailIntent.putExtra("ride", firebaseRecyclerAdapter.getItem(position));
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
