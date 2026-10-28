package com.homexa.app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.homexa.app.R;

/**
 * Services screen — UI only.
 * Shows the services layout. No filtering or navigation logic.
 */
public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
    }
}
