package com.gfive.jasdipc.loopandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView loginTV;
    private CallbackManager callbackManager;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        BackendClient.getInstance().cleanDatabase();

        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginTV = (TextView) findViewById(R.id.login_TV);
        loginButton.setReadPermissions("email", "public_profile");

        mAuth = FirebaseAuth.getInstance();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                loginTV.setText("User cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                loginTV.setText("Error logging in");

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
    }
    //MARK: Helper Functions

    private void handleFacebookAccessToken(AccessToken token) {

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

        if (user == null) {
            return;
        }

        ProfileManager.getInstance().setLocalUser(user);

        BackendClient.getInstance().doesUserExist(
                user,
                new ServerAction() {
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
                        finish();
                        startActivity(intent);
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

        if (mAuth.getCurrentUser() != null) {
            handleLogin();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        user = mAuth.getCurrentUser();

        if (user != null) {
            handleLogin();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

}