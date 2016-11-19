package com.gfive.jasdipc.loopandroid.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.R;

public class RidesViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout cardLayout;
    public TextView date;
    public ImageView userImage;
    public TextView usersName;
    public TextView journey;
    public TextView pickupTime;
    public TextView seats;
    public TextView cost;

    public RidesViewHolder(View view) {
        super(view);

        cardLayout = (RelativeLayout) view.findViewById(R.id.ride_card_layout);
        date = (TextView) view.findViewById(R.id.date_tv);
        userImage = (ImageView) view.findViewById(R.id.users_image);
        usersName = (TextView) view.findViewById(R.id.users_fullname_tv);
        journey = (TextView) view.findViewById(R.id.journey_tv);
        pickupTime = (TextView) view.findViewById(R.id.time_tv);
        seats = (TextView) view.findViewById(R.id.passengers_tv);
        cost = (TextView) view.findViewById(R.id.cost_tv);
    }
}
