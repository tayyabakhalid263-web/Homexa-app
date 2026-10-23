package com.homexa.app;

import android.app.Application;

/**
 * UI-only version.
 * No database and no app logic on startup.
 */
public class HomexaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Intentionally empty for the UI-only submission.
    }
}
