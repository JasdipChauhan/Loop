package com.gfive.jasdipc.loopandroid.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gfive.jasdipc.loopandroid.Adapters.RidesAdapter;
import com.gfive.jasdipc.loopandroid.Models.Ride;
import com.gfive.jasdipc.loopandroid.Models.User;
import com.gfive.jasdipc.loopandroid.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RidesActivity extends AppCompatActivity {

    private RecyclerView ridesRecyclerView;
    private FloatingActionButton addRideFAB;

    private RidesAdapter ridesAdapter;
    private List<Ride> rides = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        ridesRecyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        addRideFAB = (FloatingActionButton) findViewById(R.id.add_ride_FAB);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        ridesRecyclerView.setLayoutManager(llm);

        ridesAdapter = new RidesAdapter(rides);
        ridesRecyclerView.setAdapter(ridesAdapter);

        addRideFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date now = new Date();

                User user = new User("Jasdip", "Chauhan", "jc", "pass", "email", "phone num", getDrawable(R.drawable.image));
                rides.add(new Ride(user, now, "Markham", "Waterloo", "7:30pm", 5, 7.50));

                ridesAdapter.notifyItemInserted(rides.size() - 1);
            }
        });

    }
}
