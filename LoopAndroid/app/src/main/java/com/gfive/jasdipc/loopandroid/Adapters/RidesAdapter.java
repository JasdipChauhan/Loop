package com.gfive.jasdipc.loopandroid.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerLookup;
import com.gfive.jasdipc.loopandroid.Models.FirebaseRide;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RidesAdapter {

    private static RidesAdapter ridesAdapter;

    private Context mContext;
    private DatabaseReference mReference;
    private int lastPosition = -1;
    private FirebaseRecyclerAdapter<FirebaseRide, RidesViewHolder> firebaseRecyclerAdapter;

    public static RidesAdapter getInstance(Context mContext, DatabaseReference mReference) {

        if (ridesAdapter == null) {
            ridesAdapter = new RidesAdapter(mContext, mReference);
        }

        return ridesAdapter;
    }

    private RidesAdapter(final Context mContext, DatabaseReference mReference) {
        this.mContext = mContext;
        this.mReference = mReference;

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FirebaseRide, RidesViewHolder>(

                FirebaseRide.class,
                R.layout.ride_card,
                RidesViewHolder.class,
                mReference
        ) {
            @Override
            protected void populateViewHolder(final RidesViewHolder holder, FirebaseRide model, int position) {

                holder.cardLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryLightest));
                holder.usersName.setText(model.getDriver().getName());
                holder.date.setText(model.getDate());
                holder.pickup.setText(model.getPickup());
                holder.dropoff.setText(model.getDropoff());
                holder.pickupTime.setText(model.getTime());
                holder.seats.setText(model.getSeatsLeft() + "/" + model.getSeatsSize());
                holder.cost.setText(Double.toString(model.getPrice()));

                Picasso.with(mContext).load(model.getDriver().getPhoto())
                        .into(holder.userImage);


                runEnterAnimation(holder.itemView, position);
            }
        };
    }

    public FirebaseRecyclerAdapter<FirebaseRide, RidesViewHolder> getFirebaseRecyclerAdapter () {
        return firebaseRecyclerAdapter;
    }

    //Helper functions

    private List<String> getRiderList(Map<String, String> riderMap) {
        List<String> riderList = new ArrayList<>();

        ///DEBUG PURPOSES
        for (Map.Entry<String, String> entry: riderMap.entrySet()) {

            Log.i("USER KEY", entry.getKey());
            Log.i("USER ID VALUE", entry.getValue());
        }
        ///DEBUG PURPOSES

        riderList.addAll(riderMap.values());

        return riderList;
    }


    //Animations

    private void runEnterAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

