package com.gfive.jasdipc.loopandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gfive.jasdipc.loopandroid.Activities.RidesActivity;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView loginTV;
    private CallbackManager callbackManager;
    private Profile loggedInProfile;

    @Override
    protected void onResume() {
        super.onResume();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            loggedInProfile = Profile.getCurrentProfile();
            handleLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginTV = (TextView) findViewById(R.id.login_TV);
        loginButton.setReadPermissions("public_profile");

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            loggedInProfile = Profile.getCurrentProfile();
            handleLogin();
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {

                if (mProfileTracker == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.v("facebook - profile", currentProfile.getFirstName());
                            mProfileTracker.stopTracking();
                            loggedInProfile = currentProfile;
                            handleLogin();
                        }
                    };
                } else {
                    loggedInProfile = Profile.getCurrentProfile();
                    handleLogin();
                }
            }

            @Override
            public void onCancel() {
                loginTV.setText("User cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                loginTV.setText("Error logging in");

            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleLogin() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        ProfileManager.getInstance().register(loggedInProfile, accessToken);
        Intent intent = new Intent(LoginActivity.this, RidesActivity.class);
        startActivity(intent);
        finish();
    }

}
