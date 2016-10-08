package com.gfive.jasdipc.loopandroid.Adapters;

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

    List<Ride> rides;

    public RidesAdapter(List<Ride> rides) {
        this.rides = rides;

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

        holder.usersName.setText(ride.getDriver().getFullName());
        holder.date.setText(DateFormatter.getFormattedDate(ride.getDate()));
        holder.pickup.setText(ride.getPickup());
        holder.dropoff.setText(ride.getDropoff());
        holder.passengers.setText(Integer.toString(ride.getPassengers()));
        holder.cost.setText(Double.toString(ride.getCost()));
        holder.userImage.setImageDrawable(ride.getDriver().getProfilePicture());

    }

    @Override
    public int getItemCount() {
        return rides.size();
    }
}
