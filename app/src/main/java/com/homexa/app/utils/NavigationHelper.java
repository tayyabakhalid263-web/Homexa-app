package com.homexa.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.homexa.app.R;
import com.homexa.app.screens.BookingActivity;
import com.homexa.app.screens.HomeActivity;
import com.homexa.app.screens.MyBookingsActivity;
import com.homexa.app.screens.ServiceDetailsActivity;
import com.homexa.app.screens.ServicesActivity;

public final class NavigationHelper {

    private NavigationHelper() {
    }

    public static void openHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
        finishIfActivity(context);
    }

    public static void openServices(Context context, String category) {
        Intent intent = new Intent(context, ServicesActivity.class);
        if (category != null && !category.trim().isEmpty()) {
            intent.putExtra(Constants.EXTRA_CATEGORY, category);
        }
        context.startActivity(intent);
    }

    public static void openServiceDetails(Context context, int providerId) {
        Intent intent = new Intent(context, ServiceDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_PROVIDER_ID, providerId);
        context.startActivity(intent);
    }

    public static void openBooking(Context context, int providerId) {
        Intent intent = new Intent(context, BookingActivity.class);
        intent.putExtra(Constants.EXTRA_PROVIDER_ID, providerId);
        context.startActivity(intent);
    }

    public static void openMyBookings(Context context) {
        Intent intent = new Intent(context, MyBookingsActivity.class);
        context.startActivity(intent);
        finishIfActivity(context);
    }

    public static boolean onBottomNavSelected(Activity activity, int itemId, int currentItemId) {
        if (itemId == currentItemId) {
            return true;
        }
        if (itemId == R.id.nav_home) {
            openHome(activity);
            return true;
        }
        if (itemId == R.id.nav_services) {
            openServices(activity, null);
            if (!(activity instanceof HomeActivity)) {
                activity.finish();
            }
            return true;
        }
        if (itemId == R.id.nav_bookings) {
            openMyBookings(activity);
            return true;
        }
        return false;
    }

    private static void finishIfActivity(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!(activity instanceof HomeActivity)) {
                activity.finish();
            }
        }
    }
}
