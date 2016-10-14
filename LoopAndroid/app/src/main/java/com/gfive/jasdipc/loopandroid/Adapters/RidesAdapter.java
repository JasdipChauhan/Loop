package com.gfive.jasdipc.loopandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gfive.jasdipc.loopandroid.Helpers.DateFormatter;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.ViewHolders.RidesViewHolder;

import java.util.List;

public class RidesAdapter extends RecyclerView.Adapter<RidesViewHolder> {

    private List<Ride> rides;
    private Context mContext;

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
        holder.passengers.setText(Integer.toString(ride.getPassengers()));
        holder.cost.setText(Double.toString(ride.getCost()));
        //holder.userImage.setImageDrawable(ride.getDriver().getProfilePicture());
        holder.userImage.setImageDrawable(mContext.getDrawable(R.drawable.image));
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }
}
