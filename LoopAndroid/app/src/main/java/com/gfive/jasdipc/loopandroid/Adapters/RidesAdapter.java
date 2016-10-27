package com.gfive.jasdipc.loopandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.gfive.jasdipc.loopandroid.Helpers.DateFormatter;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;

import java.util.List;

public class RidesAdapter extends RecyclerView.Adapter<RidesViewHolder> {

    private List<Ride> rides;
    private Context mContext;

    private int lastPosition = -1;

    public RidesAdapter(List<Ride> rides, Context mContext) {
        this.rides = rides;
        this.mContext = mContext;
    }

    @Override
    public RidesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ride_card, parent, false);

        return new RidesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RidesViewHolder holder, int position) {

        final Ride ride = rides.get(position);

        holder.usersName.setText(ride.getDriver().getName());
        holder.date.setText(DateFormatter.formatToString(ride.getDate()));
        holder.pickup.setText(ride.getPickup());
        holder.dropoff.setText(ride.getDropoff());
        holder.pickupTime.setText(ride.getTime());
        holder.passengers.setText(Integer.toString(ride.getPassengers()));
        holder.cost.setText(Double.toString(ride.getCost()));
        holder.userImage.setImageURI(ProfileManager.getInstance(mContext).getUserProfile().profilePictureURI);

        runEnterAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public void clear() {
        int preSizeUpperRange = rides.size() - 1;
        int preSizeLowerRange = 0;

        rides.clear();
        RidesAdapter.this.notifyItemRangeRemoved(preSizeLowerRange, preSizeUpperRange);
    }

    public void add(Ride ride) {
        rides.add(ride);

        int insertedIndex = rides.size() - 1;
        RidesAdapter.this.notifyItemInserted(insertedIndex);
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

