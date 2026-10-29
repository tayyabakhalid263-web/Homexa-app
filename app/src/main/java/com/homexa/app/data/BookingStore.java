package com.homexa.app.data;

import android.content.Context;

import com.homexa.app.models.Booking;

import java.util.ArrayList;
import java.util.List;

/**
 * Booking cache + SQLite save/load.
 *
 * Teacher explanation:
 * - Local list = fast UI
 * - SQLite = permanent storage on the phone for the logged-in user
 */
public final class BookingStore {

    public interface SimpleCallback {
        void onSuccess();

        void onError(String message);
    }

    private static final List<Booking> BOOKINGS = new ArrayList<>();

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

    public static void replaceAll(List<Booking> bookings) {
        BOOKINGS.clear();
        if (bookings != null) {
            BOOKINGS.addAll(bookings);
        }
    }

    /** Save booking to SQLite for current user, then update local list. */
    public static void addBooking(Context context, Booking booking, SimpleCallback callback) {
        long userId = AuthHelper.getCurrentUserId(context);
        if (userId <= 0) {
            callback.onError("Please login again before booking.");
            return;
        }

        DatabaseHelper db = DatabaseHelper.getInstance(context);
        long newId = db.insertBooking(userId, booking);
        if (newId <= 0) {
            callback.onError("Could not save booking.");
            return;
        }

        // Rebuild Booking with real SQLite id
        Booking saved = new Booking(
                (int) newId,
                booking.getServiceCategory(),
                booking.getProviderName(),
                booking.getDate(),
                booking.getTime(),
                booking.getAddress(),
                booking.getNote(),
                booking.getEstimatedPrice(),
                booking.getStatus()
        );
        BOOKINGS.add(0, saved);
        callback.onSuccess();
    }

    /** Reload this user's bookings from SQLite into the local cache. */
    public static void refreshFromDb(Context context, SimpleCallback callback) {
        long userId = AuthHelper.getCurrentUserId(context);
        if (userId <= 0) {
            BOOKINGS.clear();
            callback.onError("User not logged in");
            return;
        }
        List<Booking> fromDb = DatabaseHelper.getInstance(context).getBookingsForUser(userId);
        replaceAll(fromDb);
        callback.onSuccess();
    }

    /** Temporary id before insert (SQLite will assign the real id). */
    public static int nextBookingId() {
        return 0;
    }
}
