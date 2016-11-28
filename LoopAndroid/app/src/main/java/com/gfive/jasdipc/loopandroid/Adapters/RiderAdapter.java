package com.gfive.jasdipc.loopandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.gfive.jasdipc.loopandroid.R;
import com.gfive.jasdipc.loopandroid.ViewHolders.RiderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by JasdipC on 2016-11-14.
 */

public class RiderAdapter {

    private Context mContext;
    private String mRideKey;

    private FirebaseRecyclerAdapter<LoopUser, RiderViewHolder> mRiderRecyclerAdapter;
    private DatabaseReference mDatabaseRef;

    public RiderAdapter(final Context mContext, String mRideKey) {

        this.mContext = mContext;
        this.mRideKey = mRideKey;

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Ride").child(mRideKey).child("riders");

        mRiderRecyclerAdapter = new FirebaseRecyclerAdapter<LoopUser, RiderViewHolder>(

                LoopUser.class,
                R.layout.rider_card,
                RiderViewHolder.class,
                mDatabaseRef
        ) {

            @Override
            protected void populateViewHolder(RiderViewHolder viewHolder, final LoopUser model, int position) {

                int count = position + 1;

                viewHolder.riderCount.setText(Integer.toString(count));
                viewHolder.ridersName.setText(model.getName());
                viewHolder.ridersEmail.setText(model.getEmail());
                viewHolder.ridersPhone.setText(model.getPhoneNumber());

                //Picasso.with(mContext).load(model.getPhoto()).into(viewHolder.ridersImage);

                viewHolder.messageRiderArea.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Uri smsURI = Uri.fromParts("sms", model.getPhoneNumber(), null);
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW, smsURI);

                        mContext.startActivity(smsIntent);
                    }

                });

                viewHolder.profileRiderArea.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Log.i("PROFILE", "PROFILE");
                    }
                });
            }
        };
    }



    public FirebaseRecyclerAdapter<LoopUser, RiderViewHolder> getmRiderRecyclerAdapter() {
        return mRiderRecyclerAdapter;
    }

}

