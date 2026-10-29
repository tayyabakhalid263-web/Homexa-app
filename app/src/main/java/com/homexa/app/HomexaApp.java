package com.homexa.app;

import android.app.Application;

import com.homexa.app.data.DatabaseHelper;
import com.homexa.app.data.ProviderStore;

/**
 * App starts here before any screen opens.
 *
 * Teacher explanation:
 * 1) Open SQLite database
 * 2) Seed sample providers once (if table empty)
 * 3) Load providers into ProviderStore for the UI
 */
public class HomexaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        db.seedProvidersIfEmpty();
        ProviderStore.replaceAll(db.getAllProviders());
    }
}
