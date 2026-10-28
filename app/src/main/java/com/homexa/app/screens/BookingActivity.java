package com.homexa.app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.homexa.app.R;

/**
 * Booking screen — UI only.
 * Shows the booking form layout. Confirm does nothing. No payment logic.
 */
public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    }
}
