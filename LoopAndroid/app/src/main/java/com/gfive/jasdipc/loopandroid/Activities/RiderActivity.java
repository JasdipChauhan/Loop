package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gfive.jasdipc.loopandroid.Adapters.RiderAdapter;
import com.gfive.jasdipc.loopandroid.Helpers.WrapContentLinearLayoutManager;
import com.gfive.jasdipc.loopandroid.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RiderActivity extends AppCompatActivity {

    private RecyclerView mRidersRecyclerView;
    private RiderAdapter mRiderAdapter;
    private String RIDE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        RIDE_KEY = getIntent().getStringExtra("RIDE_KEY");

        mRidersRecyclerView = (RecyclerView) findViewById(R.id.rider_recycler_view);
        WrapContentLinearLayoutManager wCLLM = new WrapContentLinearLayoutManager(RiderActivity.this);
        mRidersRecyclerView.setLayoutManager(wCLLM);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRiderAdapter = new RiderAdapter(RiderActivity.this, RIDE_KEY);
        mRidersRecyclerView.setAdapter(mRiderAdapter.getmRiderRecyclerAdapter());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
