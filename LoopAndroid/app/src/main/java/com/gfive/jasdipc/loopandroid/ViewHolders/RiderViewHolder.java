package com.gfive.jasdipc.loopandroid.ViewHolders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.R;

/**

 * Created by JasdipC on 2016-11-14.

 */

public class RiderViewHolder extends RecyclerView.ViewHolder{

    public TextView riderCount;
    public ImageView ridersImage;
    public TextView ridersName;
    public TextView ridersEmail;
    public TextView ridersPhone;
    public RelativeLayout messageRiderArea;
    public RelativeLayout profileRiderArea;

    public RiderViewHolder(View itemView) {

        super(itemView);

        riderCount = (TextView) itemView.findViewById(R.id.rider_counter);
        ridersImage = (ImageView) itemView.findViewById(R.id.riders_image);
        ridersName = (TextView) itemView.findViewById(R.id.riders_name);
        ridersEmail = (TextView) itemView.findViewById(R.id.riders_email);
        ridersPhone = (TextView) itemView.findViewById(R.id.riders_phone);
        messageRiderArea = (RelativeLayout) itemView.findViewById(R.id.message_rider_area);
        profileRiderArea = (RelativeLayout) itemView.findViewById(R.id.profile_rider_area);
    }

}

