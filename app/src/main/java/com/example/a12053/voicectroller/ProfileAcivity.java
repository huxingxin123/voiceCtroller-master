package com.example.a12053.voicectroller;

import static com.example.a12053.voicectroller.WelcomePage.USER_ID;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acivity);
        TextView profileId = findViewById(R.id.profile_id);
        profileId.setText(getIntent().getStringExtra(USER_ID));
    }
}