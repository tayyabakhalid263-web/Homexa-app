package com.homexa.app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.homexa.app.utils.NavigationHelper;

/**
 * Opens Electrician providers list (in-memory data).
 */
public class ElectricianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationHelper.openServices(this, "Electrician");
        finish();
    }
}
