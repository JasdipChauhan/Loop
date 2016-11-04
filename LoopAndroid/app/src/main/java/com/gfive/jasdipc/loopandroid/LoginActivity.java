package com.gfive.jasdipc.loopandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gfive.jasdipc.loopandroid.Activities.RegisterActivity;
import com.gfive.jasdipc.loopandroid.Activities.RidesActivity;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView loginTV;
    private CallbackManager callbackManager;

    private AccessToken accessToken;
    private Profile profile;


    @Override
    protected void onResume() {
        super.onResume();

        accessToken = AccessToken.getCurrentAccessToken();
        Profile.fetchProfileForCurrentAccessToken();
        profile = Profile.getCurrentProfile();

        if (accessToken != null && profile != null) {
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

        accessToken = AccessToken.getCurrentAccessToken();

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
                            profile = currentProfile;
                            handleLogin();
                        }
                    };
                } else {
                    profile = Profile.getCurrentProfile();
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

        if (accessToken != null) {

            Profile.fetchProfileForCurrentAccessToken();
            profile = Profile.getCurrentProfile();
            ProfileManager.getInstance().setLocalUser(profile, accessToken);

            BackendClient.getInstance().doesUserExist(
                    ProfileManager.getInstance().getUserProfile(),
                    new ServerResponse() {
                        @Override
                        public void response(boolean userExists) {

                            finish();
                            Intent intent;

                            if (userExists) {
                                //user has already registered
                                intent = new Intent(LoginActivity.this, RidesActivity.class);
                            } else {
                                //need to register user
                                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            }
                            startActivity(intent);


                        }
                    }
            );
        }

    }

}
