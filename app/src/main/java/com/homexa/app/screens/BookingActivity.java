package com.homexa.app.screens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.homexa.app.R;
import com.homexa.app.data.BookingStore;
import com.homexa.app.data.ProviderStore;
import com.homexa.app.models.Booking;
import com.homexa.app.models.Provider;
import com.homexa.app.utils.Constants;
import com.homexa.app.utils.NavigationHelper;

import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private LinearLayout layoutBookingForm;
    private LinearLayout layoutBookingSuccess;
    private TextView textSelectedService;
    private TextView textSelectedProvider;
    private TextView textSelectDate;
    private TextView textSelectTime;
    private EditText editAddress;
    private EditText editNote;
    private TextView textEstimatedPrice;
    private MaterialButton buttonConfirmBooking;

    private Provider selectedProvider;
    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        findViews();
        if (!loadProviderFromIntent()) {
            Toast.makeText(this, R.string.provider_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        showSelectedProvider();
        setupClickListeners();
        setupPaymentUi();
    }

    private void findViews() {
        layoutBookingForm = findViewById(R.id.layoutBookingForm);
        layoutBookingSuccess = findViewById(R.id.layoutBookingSuccess);
        textSelectedService = findViewById(R.id.textSelectedService);
        textSelectedProvider = findViewById(R.id.textSelectedProvider);
        textSelectDate = findViewById(R.id.textSelectDate);
        textSelectTime = findViewById(R.id.textSelectTime);
        editAddress = findViewById(R.id.editAddress);
        editNote = findViewById(R.id.editNote);
        textEstimatedPrice = findViewById(R.id.textEstimatedPrice);
        buttonConfirmBooking = findViewById(R.id.buttonConfirmBooking);
        buttonConfirmBooking.setText(R.string.confirm_booking);

        // Clear sample date so user must choose.
        textSelectDate.setText("");
        textSelectTime.setText("");
    }

    private boolean loadProviderFromIntent() {
        int providerId = getIntent().getIntExtra(Constants.EXTRA_PROVIDER_ID, -1);
        selectedProvider = ProviderStore.getProviderById(providerId);
        return selectedProvider != null;
    }

    private void showSelectedProvider() {
        textSelectedService.setText(selectedProvider.getCategory());
        textSelectedProvider.setText(selectedProvider.getName());
        textEstimatedPrice.setText(String.format(Locale.getDefault(),
                "PKR %d", selectedProvider.getStartingPrice()));
    }

    private void setupClickListeners() {
        TextView buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> finish());

        textSelectDate.setOnClickListener(v -> openDatePicker());
        textSelectTime.setOnClickListener(v -> openTimePicker());
        buttonConfirmBooking.setOnClickListener(v -> confirmBooking());

        TextView chipMorning = findViewById(R.id.chipTimeMorning);
        TextView chipAfternoon = findViewById(R.id.chipTimeAfternoon);
        TextView chipEvening = findViewById(R.id.chipTimeEvening);
        chipMorning.setOnClickListener(v -> applyTimeChip(chipMorning, chipAfternoon, chipEvening,
                getString(R.string.time_slot_morning)));
        chipAfternoon.setOnClickListener(v -> applyTimeChip(chipAfternoon, chipMorning, chipEvening,
                getString(R.string.time_slot_afternoon)));
        chipEvening.setOnClickListener(v -> applyTimeChip(chipEvening, chipMorning, chipAfternoon,
                getString(R.string.time_slot_evening)));

        MaterialButton buttonBackToHome = findViewById(R.id.buttonBackToHome);
        buttonBackToHome.setOnClickListener(v -> NavigationHelper.openHome(this));

        MaterialButton buttonViewMyBookings = findViewById(R.id.buttonViewMyBookings);
        buttonViewMyBookings.setOnClickListener(v -> NavigationHelper.openMyBookings(this));
    }

    private void applyTimeChip(TextView selected, TextView other1, TextView other2, String time) {
        selected.setBackgroundResource(R.drawable.bg_time_chip_selected);
        selected.setTextColor(getResources().getColor(R.color.accent, null));
        other1.setBackgroundResource(R.drawable.bg_time_chip);
        other1.setTextColor(getResources().getColor(R.color.text_primary, null));
        other2.setBackgroundResource(R.drawable.bg_time_chip);
        other2.setTextColor(getResources().getColor(R.color.text_primary, null));
        selectedTime = time;
        textSelectTime.setText(time);
    }

    private void setupPaymentUi() {
        // Visual only: COD selected, JazzCash unselected. No real payment.
        View paymentCodRow = findViewById(R.id.paymentCodRow);
        View paymentJazzCashRow = findViewById(R.id.paymentJazzCashRow);
        if (paymentCodRow != null) {
            paymentCodRow.setOnClickListener(v ->
                    Toast.makeText(this, "Cash on Delivery selected", Toast.LENGTH_SHORT).show());
        }
        if (paymentJazzCashRow != null) {
            paymentJazzCashRow.setOnClickListener(v ->
                    Toast.makeText(this, "JazzCash UI only — no real payment", Toast.LENGTH_SHORT).show());
        }
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = String.format(Locale.getDefault(),
                            "%02d/%02d/%d", dayOfMonth, month + 1, year);
                    textSelectDate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedTime = String.format(Locale.getDefault(),
                            "%02d:%02d", hourOfDay, minute);
                    textSelectTime.setText(selectedTime);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );
        dialog.show();
    }

    private void confirmBooking() {
        String address = editAddress.getText().toString().trim();
        String note = editNote.getText().toString().trim();

        if (TextUtils.isEmpty(selectedDate)) {
            Toast.makeText(this, R.string.please_select_date, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(selectedTime)) {
            Toast.makeText(this, R.string.please_select_time, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, R.string.please_enter_address, Toast.LENGTH_SHORT).show();
            return;
        }

        Booking booking = new Booking(
                BookingStore.nextBookingId(),
                selectedProvider.getCategory(),
                selectedProvider.getName(),
                selectedDate,
                selectedTime,
                address,
                note,
                selectedProvider.getStartingPrice(),
                Booking.STATUS_CONFIRMED
        );

        buttonConfirmBooking.setEnabled(false);

        // Save booking into SQLite for the logged-in user
        BookingStore.addBooking(this, booking, new BookingStore.SimpleCallback() {
            @Override
            public void onSuccess() {
                buttonConfirmBooking.setEnabled(true);
                layoutBookingForm.setVisibility(View.GONE);
                layoutBookingSuccess.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message) {
                buttonConfirmBooking.setEnabled(true);
                Toast.makeText(BookingActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
