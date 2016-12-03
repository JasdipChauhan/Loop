package com.gfive.jasdipc.loopandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gfive.jasdipc.loopandroid.Clients.BackendClient;
import com.gfive.jasdipc.loopandroid.Interfaces.ServerAction;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.R;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity {

    private ImageView mRegisterImage;
    private TextView mRegisterName;
    private TextView mRegisterEmail;
    private EditText mRegisterPhoneNumber;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRegisterImage = (ImageView) findViewById(R.id.register_photo);
        mRegisterName = (TextView) findViewById(R.id.register_name);
        mRegisterEmail = (TextView) findViewById(R.id.register_email);
        mRegisterPhoneNumber = (EditText) findViewById(R.id.register_phone_number);
        mRegisterButton = (Button) findViewById(R.id.register_button);

        FirebaseUser currentUser = ProfileManager.getInstance().getFirebaseUser();

        Picasso.with(RegisterActivity.this).load(currentUser.getPhotoUrl().toString())
                .into(mRegisterImage);
        mRegisterName.setText(currentUser.getDisplayName());
        mRegisterEmail.setText(currentUser.getEmail());

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                final String phoneNumber = mRegisterPhoneNumber.getText().toString().trim();

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
                    Toast.makeText(RegisterActivity.this, "Invalid Phone Number...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
