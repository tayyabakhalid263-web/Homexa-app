package com.homexa.app.models;

/**
 * One booking made by the user.
 * Status can be: Confirmed, Pending, or Completed.
 */
public class Booking {

    public static final String STATUS_CONFIRMED = "Confirmed";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_COMPLETED = "Completed";

    private final int id;
    private final String serviceCategory;
    private final String providerName;
    private final String date;
    private final String time;
    private final String address;
    private final String note;
    private final int estimatedPrice;
    private final String status;

    public Booking(
            int id,
            String serviceCategory,
            String providerName,
            String date,
            String time,
            String address,
            String note,
            int estimatedPrice,
            String status
    ) {
        this.id = id;
        this.serviceCategory = serviceCategory;
        this.providerName = providerName;
        this.date = date;
        this.time = time;
        this.address = address;
        this.note = note;
        this.estimatedPrice = estimatedPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }

    public int getEstimatedPrice() {
        return estimatedPrice;
    }

    public String getStatus() {
        return status;
    }
}
