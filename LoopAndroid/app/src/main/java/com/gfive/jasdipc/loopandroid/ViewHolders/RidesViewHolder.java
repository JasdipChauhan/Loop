package com.gfive.jasdipc.loopandroid.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.R;

public class RidesViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView date;
    public RelativeLayout dateView;
    public ImageView userImage;
    public TextView usersName;
    public TextView journey;
    public TextView pickupTime;
    public TextView seats;
    public TextView cost;
    public View myRideView;

    public RidesViewHolder(View view) {
        super(view);

        cardView = (CardView) view.findViewById(R.id.card_view);
        myRideView = view.findViewById(R.id.my_ride_view);
        date = (TextView) view.findViewById(R.id.date_tv);
        dateView = (RelativeLayout) view.findViewById(R.id.date_view);
        userImage = (ImageView) view.findViewById(R.id.users_image);
        usersName = (TextView) view.findViewById(R.id.users_fullname_tv);
        journey = (TextView) view.findViewById(R.id.journey_tv);
        pickupTime = (TextView) view.findViewById(R.id.time_tv);
        seats = (TextView) view.findViewById(R.id.passengers_tv);
        cost = (TextView) view.findViewById(R.id.cost_tv);
    }
}