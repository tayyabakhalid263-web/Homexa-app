package com.homexa.app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.homexa.app.R;

/**
 * My Bookings screen — UI only.
 * Shows static booking cards. No database and no navigation.
 */
public class MyBookingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
    }
}
