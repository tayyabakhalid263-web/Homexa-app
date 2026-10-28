package com.homexa.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * UI-only version.
 * Shows a simple layout. No navigation and no business logic.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
