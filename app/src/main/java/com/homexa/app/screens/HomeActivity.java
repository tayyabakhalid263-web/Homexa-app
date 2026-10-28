package com.homexa.app.screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.homexa.app.R;
import com.homexa.app.adapters.CategoryAdapter;
import com.homexa.app.adapters.ProviderAdapter;
import com.homexa.app.data.BookingStore;
import com.homexa.app.data.ProviderStore;
import com.homexa.app.data.SampleData;
import com.homexa.app.models.Booking;
import com.homexa.app.models.Provider;
import com.homexa.app.utils.NavigationHelper;
import com.homexa.app.utils.StatusHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private EditText editSearch;
    private RecyclerView recyclerCategories;
    private RecyclerView recyclerPopularServices;
    private View recentBookingCard;
    private TextView textNoRecentBooking;
    private BottomNavigationView bottomNavigation;

    private ProviderAdapter popularAdapter;
    private final List<Provider> popularProviders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        setupCategories();
        setupPopularServices();
        setupSearch();
        setupRecentBooking();
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecentBooking();
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    private void findViews() {
        View searchBar = findViewById(R.id.searchBar);
        editSearch = searchBar.findViewById(R.id.editSearch);
        recyclerCategories = findViewById(R.id.recyclerCategories);
        recyclerPopularServices = findViewById(R.id.recyclerPopularServices);
        recentBookingCard = findViewById(R.id.recentBookingCard);
        textNoRecentBooking = findViewById(R.id.textNoRecentBooking);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        TextView textViewAllPopular = findViewById(R.id.textViewAllPopular);
        textViewAllPopular.setOnClickListener(v -> NavigationHelper.openServices(this, null));
    }

    private void setupCategories() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(category ->
                NavigationHelper.openServices(this, category.getName()));
        recyclerCategories.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCategories.setAdapter(categoryAdapter);
        categoryAdapter.submitList(SampleData.getCategories());
    }

    private void setupPopularServices() {
        popularAdapter = new ProviderAdapter(provider ->
                NavigationHelper.openServiceDetails(this, provider.getId()), true);
        recyclerPopularServices.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerPopularServices.setAdapter(popularAdapter);
        popularProviders.clear();
        popularProviders.addAll(ProviderStore.getPopularProviders());
        popularAdapter.submitList(popularProviders);
    }

    private void setupSearch() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPopular(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterPopular(String query) {
        if (query == null || query.trim().isEmpty()) {
            popularAdapter.submitList(popularProviders);
            return;
        }
        String needle = query.trim().toLowerCase(Locale.getDefault());
        List<Provider> filtered = new ArrayList<>();
        for (Provider provider : popularProviders) {
            if (provider.getName().toLowerCase(Locale.getDefault()).contains(needle)
                    || provider.getCategory().toLowerCase(Locale.getDefault()).contains(needle)) {
                filtered.add(provider);
            }
        }
        popularAdapter.submitList(filtered);
    }

    private void setupRecentBooking() {
        Booking latest = BookingStore.getLatestBooking();
        if (latest == null) {
            recentBookingCard.setVisibility(View.GONE);
            textNoRecentBooking.setVisibility(View.VISIBLE);
            return;
        }
        recentBookingCard.setVisibility(View.VISIBLE);
        textNoRecentBooking.setVisibility(View.GONE);

        TextView textBookingService = recentBookingCard.findViewById(R.id.textBookingService);
        TextView textBookingProvider = recentBookingCard.findViewById(R.id.textBookingProvider);
        TextView textBookingDateTime = recentBookingCard.findViewById(R.id.textBookingDateTime);
        TextView textBookingAddress = recentBookingCard.findViewById(R.id.textBookingAddress);
        TextView textBookingStatus = recentBookingCard.findViewById(R.id.textBookingStatus);
        TextView textBookingPrice = recentBookingCard.findViewById(R.id.textBookingPrice);

        textBookingService.setText(latest.getServiceCategory());
        textBookingProvider.setText(latest.getProviderName());
        textBookingDateTime.setText(latest.getDate() + " · " + latest.getTime());
        textBookingAddress.setText(latest.getAddress());
        StatusHelper.applyStatusStyle(textBookingStatus, latest.getStatus());
        if (textBookingPrice != null) {
            textBookingPrice.setText(String.format(Locale.getDefault(),
                    "PKR %d", latest.getEstimatedPrice()));
        }
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item ->
                NavigationHelper.onBottomNavSelected(this, item.getItemId(), R.id.nav_home));
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }
}
