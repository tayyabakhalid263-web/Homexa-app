package com.homexa.app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.homexa.app.R;

/**
 * Service details screen — UI only.
 * Shows the details layout. Book Now does nothing.
 */
public class ServiceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
    }
}
