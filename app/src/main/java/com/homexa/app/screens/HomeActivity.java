package com.homexa.app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.homexa.app.R;

/**
 * Home screen — UI only.
 * Shows the home layout. No search, navigation, or booking logic.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
