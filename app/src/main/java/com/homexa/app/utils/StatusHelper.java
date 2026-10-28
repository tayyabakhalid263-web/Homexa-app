package com.homexa.app.utils;

import android.widget.TextView;

import com.homexa.app.R;
import com.homexa.app.models.Booking;

public final class StatusHelper {

    private StatusHelper() {
    }

    public static void applyStatusStyle(TextView statusView, String status) {
        if (status == null) {
            return;
        }
        if (Booking.STATUS_PENDING.equalsIgnoreCase(status)) {
            statusView.setBackgroundResource(R.drawable.bg_status_pending);
            statusView.setTextColor(statusView.getResources().getColor(R.color.status_pending, null));
        } else if (Booking.STATUS_COMPLETED.equalsIgnoreCase(status)) {
            statusView.setBackgroundResource(R.drawable.bg_status_completed);
            statusView.setTextColor(statusView.getResources().getColor(R.color.status_completed, null));
        } else {
            statusView.setBackgroundResource(R.drawable.bg_status_confirmed);
            statusView.setTextColor(statusView.getResources().getColor(R.color.status_confirmed, null));
        }
        statusView.setText(status);
    }
}
