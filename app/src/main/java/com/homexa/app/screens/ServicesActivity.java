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
import com.homexa.app.adapters.ProviderAdapter;
import com.homexa.app.data.ProviderStore;
import com.homexa.app.models.Provider;
import com.homexa.app.utils.Constants;
import com.homexa.app.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    private TextView textServicesTitle;
    private TextView textServicesSubtitle;
    private TextView textNoProviders;
    private TextView buttonBack;
    private EditText editSearch;
    private RecyclerView recyclerProviders;
    private BottomNavigationView bottomNavigation;

    private ProviderAdapter providerAdapter;
    private String selectedCategory;
    private final List<Provider> currentProviders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        selectedCategory = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);
        findViews();
        setupList();
        setupSearch();
        setupBottomNavigation();
        loadProviders();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigation.setSelectedItemId(R.id.nav_services);
    }

    private void findViews() {
        textServicesTitle = findViewById(R.id.textServicesTitle);
        textServicesSubtitle = findViewById(R.id.textServicesSubtitle);
        textNoProviders = findViewById(R.id.textNoProviders);
        buttonBack = findViewById(R.id.buttonBack);
        recyclerProviders = findViewById(R.id.recyclerProviders);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        View searchBar = findViewById(R.id.searchBar);
        editSearch = searchBar.findViewById(R.id.editSearch);

        if (selectedCategory != null && !selectedCategory.trim().isEmpty()) {
            textServicesTitle.setText(selectedCategory);
            textServicesSubtitle.setText(getString(R.string.providers_for, selectedCategory));
            buttonBack.setVisibility(View.VISIBLE);
            buttonBack.setOnClickListener(v -> finish());
        } else {
            textServicesTitle.setText(R.string.nav_services);
            textServicesSubtitle.setText(R.string.services_subtitle);
            buttonBack.setVisibility(View.GONE);
        }
    }

    private void setupList() {
        providerAdapter = new ProviderAdapter(provider ->
                NavigationHelper.openServiceDetails(this, provider.getId()), false);
        recyclerProviders.setLayoutManager(new LinearLayoutManager(this));
        recyclerProviders.setAdapter(providerAdapter);
    }

    private void setupSearch() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applySearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadProviders() {
        currentProviders.clear();
        currentProviders.addAll(ProviderStore.getProvidersByCategory(selectedCategory));
        providerAdapter.submitList(currentProviders);
        updateEmptyState(currentProviders.isEmpty());
    }

    private void applySearch(String query) {
        List<Provider> filtered = ProviderStore.searchProviders(query, selectedCategory);
        providerAdapter.submitList(filtered);
        updateEmptyState(filtered.isEmpty());
    }

    private void updateEmptyState(boolean empty) {
        textNoProviders.setVisibility(empty ? View.VISIBLE : View.GONE);
        recyclerProviders.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_services) {
                selectedCategory = null;
                textServicesTitle.setText(R.string.nav_services);
                textServicesSubtitle.setText(R.string.services_subtitle);
                buttonBack.setVisibility(View.GONE);
                editSearch.setText("");
                loadProviders();
                return true;
            }
            return NavigationHelper.onBottomNavSelected(this, itemId, R.id.nav_services);
        });
        bottomNavigation.setSelectedItemId(R.id.nav_services);
    }
}
