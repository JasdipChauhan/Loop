package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.AppConstants;
import com.gfive.jasdipc.loopandroid.R;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private ImageView mRegisterImage;
    private TextView mRegisterName;
    private EditText mRegisterPhoneNumber;
    private Button mRegisterButton;
    private ImageView mRegistrationBackground;

    private boolean mIsRegistrationButtonEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRegisterImage = (ImageView) findViewById(R.id.register_photo);
        mRegisterName = (TextView) findViewById(R.id.register_name);
        mRegisterPhoneNumber = (EditText) findViewById(R.id.register_phone_number);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegistrationBackground = (ImageView) findViewById(R.id.registration_background_image);

        //set background image
        //new SetBackgroundAsync().execute();

        FirebaseUser currentUser = ProfileManager.getInstance().getFirebaseUser();

        Picasso.with(RegisterActivity.this).load(currentUser.getPhotoUrl().toString())
                .into(mRegisterImage);
        mRegisterName.setText(currentUser.getDisplayName());

        mRegisterPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mRegisterPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((count > 0) && !mIsRegistrationButtonEnabled) {
                    mRegisterButton.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.enabled_sign_up_bg));
                    mIsRegistrationButtonEnabled = true;
                    return;
                }

                if ((count == 0) && mIsRegistrationButtonEnabled) {
                    mRegisterButton.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.disabled_sign_up_bg));
                    mIsRegistrationButtonEnabled = false;
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                final String phoneNumber = PhoneNumberUtils.normalizeNumber(mRegisterPhoneNumber.getText().toString().trim());

                if (phoneNumber.length() == 10) {

                    BackendClient.getInstance().registerUser(
                            ProfileManager.getInstance().getFirebaseUser(),
                            phoneNumber,
                            new ServerAction() {
                                @Override
                                public void response(boolean isSuccessful) {
                                    if (isSuccessful) {

                                        ProfileManager.getInstance().setupLoopUser(phoneNumber);

                                        Intent i = new Intent(RegisterActivity.this, RidesActivity.class);
                                        startActivity(i);
                                    }
                                }
                            }
                    );

                } else {
                    Snackbar.make(findViewById(R.id.activity_register), "Invalid phone number...", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private class SetBackgroundAsync extends AsyncTask<Void, Void, Drawable> {

        private Animation fadeIn;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        }

        @Override
        protected Drawable doInBackground(Void... params) {

            try {
                return new BitmapDrawable(getResources(), Picasso.with(getApplicationContext()).load(AppConstants.LOGIN_ACTIVITY_BACKGROUND_IMAGE).get());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);

            mRegistrationBackground.setBackground(drawable);
            mRegistrationBackground.startAnimation(fadeIn);
        }
    }
}
