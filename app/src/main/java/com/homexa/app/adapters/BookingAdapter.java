package com.homexa.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homexa.app.R;
import com.homexa.app.models.Booking;
import com.homexa.app.utils.StatusHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final List<Booking> bookings = new ArrayList<>();

    public void submitList(List<Booking> items) {
        bookings.clear();
        if (items != null) {
            bookings.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking_card, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.bind(bookings.get(position));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        private final TextView textBookingService;
        private final TextView textBookingProvider;
        private final TextView textBookingDateTime;
        private final TextView textBookingAddress;
        private final TextView textBookingStatus;
        private final TextView textBookingPrice;

        BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            textBookingService = itemView.findViewById(R.id.textBookingService);
            textBookingProvider = itemView.findViewById(R.id.textBookingProvider);
            textBookingDateTime = itemView.findViewById(R.id.textBookingDateTime);
            textBookingAddress = itemView.findViewById(R.id.textBookingAddress);
            textBookingStatus = itemView.findViewById(R.id.textBookingStatus);
            textBookingPrice = itemView.findViewById(R.id.textBookingPrice);
        }

        void bind(Booking booking) {
            textBookingService.setText(booking.getServiceCategory());
            textBookingProvider.setText(booking.getProviderName());
            textBookingDateTime.setText(booking.getDate() + " · " + booking.getTime());
            textBookingAddress.setText(booking.getAddress());
            StatusHelper.applyStatusStyle(textBookingStatus, booking.getStatus());
            if (textBookingPrice != null) {
                textBookingPrice.setText(String.format(Locale.getDefault(),
                        "PKR %d", booking.getEstimatedPrice()));
            }
        }
    }
}
