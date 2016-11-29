package com.gfive.jasdipc.loopandroid.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.R;

/**
 * Created by JasdipC on 2016-11-28.
 */

public class DriverViewHolder extends RecyclerView.ViewHolder {

    public TextView costView;
    public ImageView capacityImage;
    public TextView capacityText;
    public TextView journeyText;
    public TextView timeText;
    public TextView riderText;


    public DriverViewHolder(View itemView) {
        super(itemView);

        costView = (TextView) itemView.findViewById(R.id.driver_cost_tv);
        capacityImage = (ImageView) itemView.findViewById(R.id.driver_capacity_image);
        capacityText = (TextView) itemView.findViewById(R.id.driver_passenger_tv);
        journeyText = (TextView) itemView.findViewById(R.id.driver_journey_tv);
        timeText = (TextView) itemView.findViewById(R.id.driver_time_tv);
        riderText = (TextView) itemView.findViewById(R.id.driver_rider_count_tv);
    }
}
