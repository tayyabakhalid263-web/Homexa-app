package com.homexa.app.screens;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.homexa.app.R;
import com.homexa.app.data.ProviderStore;
import com.homexa.app.models.Provider;
import com.homexa.app.utils.Constants;
import com.homexa.app.utils.NavigationHelper;

import java.util.Locale;

public class ServiceDetailsActivity extends AppCompatActivity {

    private ImageView imageProvider;
    private TextView textProviderName;
    private TextView textServiceType;
    private TextView textRatingValue;
    private TextView textExperience;
    private TextView textDescription;
    private TextView textAvailableTime;
    private TextView textStartingPrice;
    private MaterialButton buttonBookNow;

    private Provider selectedProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        findViews();
        if (!loadProviderFromIntent()) {
            Toast.makeText(this, R.string.provider_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        showProviderDetails();
        setupClickListeners();
    }

    private void findViews() {
        imageProvider = findViewById(R.id.imageProvider);
        textProviderName = findViewById(R.id.textProviderName);
        textServiceType = findViewById(R.id.textServiceType);
        textExperience = findViewById(R.id.textExperience);
        textDescription = findViewById(R.id.textDescription);
        textAvailableTime = findViewById(R.id.textAvailableTime);
        textStartingPrice = findViewById(R.id.textStartingPrice);
        textRatingValue = findViewById(R.id.textRatingValue);
        buttonBookNow = findViewById(R.id.buttonBookNow);
        buttonBookNow.setText(R.string.book_now);
    }

    private boolean loadProviderFromIntent() {
        int providerId = getIntent().getIntExtra(Constants.EXTRA_PROVIDER_ID, -1);
        selectedProvider = ProviderStore.getProviderById(providerId);
        return selectedProvider != null;
    }

    private void showProviderDetails() {
        imageProvider.setImageResource(selectedProvider.getImageResId());
        textProviderName.setText(selectedProvider.getName());
        textServiceType.setText(selectedProvider.getCategory());
        textRatingValue.setText(String.format(Locale.getDefault(),
                "%.1f", selectedProvider.getRating()));
        textExperience.setText(String.format(Locale.getDefault(),
                "%d years", selectedProvider.getExperienceYears()));
        textDescription.setText(selectedProvider.getDescription());
        textAvailableTime.setText(selectedProvider.getAvailableTime());
        textStartingPrice.setText(String.format(Locale.getDefault(),
                "PKR %d", selectedProvider.getStartingPrice()));
    }

    private void setupClickListeners() {
        TextView buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> finish());
        buttonBookNow.setOnClickListener(v ->
                NavigationHelper.openBooking(this, selectedProvider.getId()));
    }
}
