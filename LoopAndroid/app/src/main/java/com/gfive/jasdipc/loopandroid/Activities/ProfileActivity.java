package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Models.AppConstants;
import com.gfive.jasdipc.loopandroid.Models.LoopUser;
import com.gfive.jasdipc.loopandroid.R;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    private LoopUser user;

    private ImageView mProfilePhoto;
    private TextView mProfileName;
    private TextView mProfilePhone;
    private Button mMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProfilePhoto = (ImageView) findViewById(R.id.profile_photo);
        mProfileName = (TextView) findViewById(R.id.profile_name);
        mProfilePhone = (TextView) findViewById(R.id.profile_phone_number);
        mMessageButton = (Button) findViewById(R.id.profile_message_button);

        user = getIntent().getParcelableExtra(AppConstants.TO_LOOP_USER);

        mProfileName.setText(user.getName());
        mProfilePhone.setText(user.getPhoneNumber());
        Picasso.with(this).load(user.getPhoto()).into(mProfilePhoto);

        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsURI = Uri.fromParts("sms", user.getPhoneNumber(), null);
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, smsURI);

                startActivity(smsIntent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
