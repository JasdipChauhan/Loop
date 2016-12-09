package com.gfive.jasdipc.loopandroid.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.LoopRide;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.ViewHolders.DriverViewHolder;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RidesAdapter {

    private Context mContext;
    private DatabaseReference mReference;

    private int lastPosition = -1;

    private FirebaseRecyclerAdapter<LoopRide, RidesViewHolder> firebaseRecyclerAdapter;
    private FirebaseRecyclerAdapter<LoopRide, RidesViewHolder> localRecyclerAdapter;
    private FirebaseRecyclerAdapter<LoopRide, DriverViewHolder> driverRecyclerAdapter;

    private Map<String, Long> dateMapper = new HashMap<>();

    public RidesAdapter(final Context mContext, DatabaseReference mReference) {
        this.mContext = mContext;
        this.mReference = mReference;
    }

    public FirebaseRecyclerAdapter<LoopRide, RidesViewHolder> getAllRidesAdapter(final Set<String> savedRides) {

        final Query query = mReference.orderByChild("date");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<LoopRide, RidesViewHolder>(

                LoopRide.class,
                R.layout.ride_card,
                RidesViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(final RidesViewHolder holder, LoopRide model, int position) {
                String ref = getRef(position).getKey().toString();

                if (model.getSeatsLeft() < 1) {
                    holder.cardView.setVisibility(View.GONE);
                }

                fillViewHolder(holder, model, position, ref, savedRides);
            }
        };

        return firebaseRecyclerAdapter;
    }

    public FirebaseRecyclerAdapter<LoopRide, DriverViewHolder> getDriverRidesAdapter(final Set<String> driverRideIDs) {

        driverRecyclerAdapter = new FirebaseRecyclerAdapter<LoopRide, DriverViewHolder>(
                LoopRide.class,
                R.layout.driver_card,
                DriverViewHolder.class,
                mReference
        ) {
            @Override
            protected void populateViewHolder(final DriverViewHolder viewHolder, final LoopRide model, int position) {

                String ref = getRef(position).getKey().toString();

                if (!driverRideIDs.contains(ref)) {
                    viewHolder.driverCard.setVisibility(View.GONE);
                    return;
                }

                String filledSpots = Integer.toString(model.getSeatsSize() - model.getSeatsLeft());
                String journeyTextString = model.getPickup().concat(" to ").concat(model.getDropoff());
                String capacityTextString = filledSpots + "/" + model.getSeatsSize() + " Filled";

                viewHolder.dateText.setText(FormatHelper.toReadableFormat(model.getDate()));
                viewHolder.timeText.setText(model.getTime());
                viewHolder.journeyText.setText(journeyTextString);
                viewHolder.capacityText.setText(capacityTextString);
                viewHolder.costView.setText("$" + model.getPrice());
                viewHolder.riderText.setText(filledSpots + " riders");

            }
        };


        return driverRecyclerAdapter;

    }

    public FirebaseRecyclerAdapter<LoopRide, RidesViewHolder> getRiderRidesAdapter(final Set<String> savedRides) {

        final Query query = mReference.orderByChild("date");

        localRecyclerAdapter = new FirebaseRecyclerAdapter<LoopRide, RidesViewHolder>(
                LoopRide.class,
                R.layout.ride_card,
                RidesViewHolder.class,
                query

        ) {

            @Override
            protected void populateViewHolder(RidesViewHolder holder, LoopRide model, int position) {

                //filter rides for the saved ones by the user
                String ref = getRef(position).getKey().toString();
                if (!savedRides.contains(ref)) {
                    holder.cardView.setVisibility(View.GONE);
                    return;
                }

                fillViewHolder(holder, model, position, ref, savedRides);
            }
        };


        return localRecyclerAdapter;
    }

    //Helper functions

    private void fillViewHolder(RidesViewHolder holder, LoopRide model, int position, String rideKey, Set<String> myRides) {

        String currentRideDate = FormatHelper.toReadableFormat(model.getDate());

        /////

        //update with the more recent date

        if ((!dateMapper.containsKey(currentRideDate)) || (model.getDate() <= dateMapper.get(currentRideDate))) {
            dateMapper.put(currentRideDate, model.getDate());
            holder.dateView.setVisibility(View.VISIBLE);
            holder.cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, mContext.getResources().getDisplayMetrics())
            ));

        } else {

            holder.dateView.setVisibility(View.GONE);
            holder.cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, mContext.getResources().getDisplayMetrics())
            ));

        }

        holder.usersName.setText(FormatHelper.shortHandName(model.getDriver().getName()));
        holder.date.setText(FormatHelper.toReadableFormat(model.getDate()));
        holder.journey.setText(model.getPickup() + " to " + model.getDropoff());
        holder.pickupTime.setText(model.getTime());
        holder.seats.setText(model.getSeatsLeft() + "/" + model.getSeatsSize());
        holder.cost.setText("$".concat(Integer.toString(model.getPrice())));

        if (myRides.contains(rideKey)) {
            holder.myRideView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        }

        Picasso.with(mContext).load(model.getDriver().getPhoto()).into(holder.userImage);


        runEnterAnimation(holder.itemView, position);
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