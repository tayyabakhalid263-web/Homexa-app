package com.homexa.app.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.homexa.app.R;
import com.homexa.app.adapters.BookingAdapter;
import com.homexa.app.data.BookingStore;
import com.homexa.app.models.Booking;
import com.homexa.app.utils.NavigationHelper;

import java.util.List;

/**
 * Shows bookings for the logged-in user (loaded from SQLite).
 */
public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerBookings;
    private TextView textNoBookings;
    private BottomNavigationView bottomNavigation;
    private BookingAdapter bookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        recyclerBookings = findViewById(R.id.recyclerBookings);
        textNoBookings = findViewById(R.id.textNoBookings);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bookingAdapter = new BookingAdapter();
        recyclerBookings.setLayoutManager(new LinearLayoutManager(this));
        recyclerBookings.setAdapter(bookingAdapter);

        bottomNavigation.setOnItemSelectedListener(item ->
                NavigationHelper.onBottomNavSelected(this, item.getItemId(), R.id.nav_bookings));
        bottomNavigation.setSelectedItemId(R.id.nav_bookings);

        loadBookings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigation.setSelectedItemId(R.id.nav_bookings);
        loadBookings();
    }

    private void loadBookings() {
        BookingStore.refreshFromDb(this, new BookingStore.SimpleCallback() {
            @Override
            public void onSuccess() {
                showBookings();
            }

            @Override
            public void onError(String message) {
                showBookings();
            }
        });
    }

    private void showBookings() {
        List<Booking> bookings = BookingStore.getAllBookings();
        bookingAdapter.submitList(bookings);
        boolean empty = bookings.isEmpty();
        textNoBookings.setVisibility(empty ? View.VISIBLE : View.GONE);
        recyclerBookings.setVisibility(empty ? View.GONE : View.VISIBLE);
    }
}
