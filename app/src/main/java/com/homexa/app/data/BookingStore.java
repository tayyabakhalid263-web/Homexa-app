package com.homexa.app.data;

import com.homexa.app.models.Booking;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory bookings list (no database).
 */
public final class BookingStore {

    private static final List<Booking> BOOKINGS = new ArrayList<>(SampleData.getSampleBookings());
    private static int nextId = 100;

    private BookingStore() {
    }

    public static List<Booking> getAllBookings() {
        return new ArrayList<>(BOOKINGS);
    }

    public static Booking getLatestBooking() {
        if (BOOKINGS.isEmpty()) {
            return null;
        }
        return BOOKINGS.get(0);
    }

    public static void addBooking(Booking booking) {
        BOOKINGS.add(0, booking);
    }

    public static int nextBookingId() {
        return nextId++;
    }
}
