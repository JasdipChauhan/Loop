package com.gfive.jasdipc.loopandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gfive.jasdipc.loopandroid.Activities.RidesActivity;
import com.gfive.jasdipc.loopandroid.Activities.RegisterActivity;
import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private Button facebookButton;
    private LinearLayout backgroundLayout;

    private boolean canLogin = false;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        getSupportActionBar().hide();

        BackendClient.getInstance().cleanDatabase();

        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        dialog = new ProgressDialog(this);

        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        facebookButton = (Button) findViewById(R.id.facebook_button);
        backgroundLayout = (LinearLayout) findViewById(R.id.activity_login);

        new SetBackgroundAsync().execute();
        loginButton.setReadPermissions("email", "public_profile");

        mAuth = FirebaseAuth.getInstance();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Snackbar.make(findViewById(R.id.activity_login), "Cancelled...", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Snackbar.make(findViewById(R.id.activity_login), "Error logging in...", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.i("USER", user.getUid());
                } else {
                    // User is signed out

                }

            }
        };

        canLogin = true;
    }

    public void facebookClickedAction(View view) {
        loginButton.performClick();
    }

    //MARK: Helper Functions

    private void handleFacebookAccessToken(AccessToken token) {

        showDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LOGIN ACTIVITY", "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("LOGIN ACTIVITY", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        handleLogin();
                    }
                });
    }


    private void handleLogin() {

        showDialog();

        if (user == null) {
            return;
        }

        ProfileManager.getInstance().setLocalUser(user);

        canLogin = true;

        BackendClient.getInstance().doesUserExist(
                user,
                new ServerAction() {
                    @Override
                    public void response(boolean userExists) {

                        Log.i("RESPONSE", "NEXT ACTIVITY");

                        Intent intent;
                        if (userExists && canLogin) {
                            //user has already registered
                            intent = new Intent(LoginActivity.this, RidesActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        if (!userExists && canLogin) {
                            intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        hideDialog();
                        canLogin = false;
                        finish();
                    }
                }
        );
    }

    //MARK: Lifecycle Methods

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        canLogin = false;
        if (mAuth.getCurrentUser() != null) {
            handleLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        user = mAuth.getCurrentUser();

        canLogin = false;
        if (user != null) {
            handleLogin();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        canLogin = false;
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.setMessage("Logging in...");
            dialog.show();
        }

    }

    private void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
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

            backgroundLayout.setBackground(drawable);
            backgroundLayout.startAnimation(fadeIn);
        }
    }
}