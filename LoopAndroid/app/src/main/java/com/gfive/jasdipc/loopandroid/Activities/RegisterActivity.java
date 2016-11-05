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
import com.gfive.jasdipc.loopandroid.Interfaces.ServerResponse;
import com.gfive.jasdipc.loopandroid.Managers.ProfileManager;
import com.gfive.jasdipc.loopandroid.Models.UserProfile;
import com.gfive.jasdipc.loopandroid.R;
import com.google.android.gms.vision.text.Text;
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

        mRegisterImage = (ImageView) findViewById(R.id.register_photo);
        mRegisterName = (TextView) findViewById(R.id.register_name);
        mRegisterEmail = (TextView) findViewById(R.id.register_email);
        mRegisterPhoneNumber = (EditText) findViewById(R.id.register_phone_number);
        mRegisterButton = (Button) findViewById(R.id.register_button);

        UserProfile currentUser = ProfileManager.getInstance().getUserProfile();

        Picasso.with(RegisterActivity.this).load(currentUser.profilePictureURI)
                .into(mRegisterImage);
        mRegisterName.setText(currentUser.name);
        mRegisterEmail.setText(currentUser.email);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String phoneNumber = mRegisterPhoneNumber.getText().toString().trim();

                if (phoneNumber.length() == 10) {

                    BackendClient.getInstance().registerUser(
                            ProfileManager.getInstance().getFirebaseUser(),
                            phoneNumber,
                            new ServerResponse() {
                                @Override
                                public void response(boolean isSuccessful) {
                                    if (isSuccessful) {
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
