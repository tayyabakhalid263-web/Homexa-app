package com.homexa.app.data;

import com.homexa.app.R;
import com.homexa.app.models.Booking;
import com.homexa.app.models.Provider;
import com.homexa.app.models.ServiceCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample categories + providers.
 * Providers are copied into SQLite once on first app launch (see DatabaseHelper).
 */
public final class SampleData {

    private SampleData() {
    }

    public static List<ServiceCategory> getCategories() {
        List<ServiceCategory> categories = new ArrayList<>();
        categories.add(new ServiceCategory("Electrician", R.drawable.ic_category_electrician));
        categories.add(new ServiceCategory("Plumber", R.drawable.ic_category_plumber));
        categories.add(new ServiceCategory("AC Repair", R.drawable.ic_category_ac));
        categories.add(new ServiceCategory("Home Cleaning", R.drawable.ic_category_cleaning));
        categories.add(new ServiceCategory("Painting", R.drawable.ic_category_painting));
        return categories;
    }

    public static List<Provider> getProviders() {
        List<Provider> providers = new ArrayList<>();

        providers.add(new Provider(1, "Ahmed Electric Services", "Electrician",
                4.8f, 5, 1500,
                "Reliable electrician for wiring, switches, fans, and home electrical repairs.",
                "9:00 AM - 6:00 PM", R.drawable.ic_avatar_1));

        providers.add(new Provider(2, "Bright Wire Solutions", "Electrician",
                4.4f, 3, 1300,
                "Affordable electrical repair and installation for homes and shops.",
                "11:00 AM - 7:00 PM", R.drawable.ic_avatar_2));

        providers.add(new Provider(3, "PowerFix Electric Co.", "Electrician",
                4.9f, 9, 1800,
                "Specialist in breaker panels, LED conversion, and emergency faults.",
                "9:00 AM - 8:00 PM", R.drawable.ic_avatar_3));

        providers.add(new Provider(4, "Ali Plumbing Services", "Plumber",
                4.6f, 7, 1200,
                "Expert plumber for leakage, pipe fitting, bathroom and kitchen plumbing.",
                "10:00 AM - 7:00 PM", R.drawable.ic_avatar_1));

        providers.add(new Provider(5, "Fresh Flow Plumbing", "Plumber",
                4.3f, 5, 1100,
                "Fast response for blocked drains, taps, and water tank connections.",
                "9:00 AM - 5:00 PM", R.drawable.ic_avatar_2));

        providers.add(new Provider(6, "AquaPro Plumbers", "Plumber",
                4.7f, 6, 1350,
                "Bathroom renovations, geyser installation, and water pressure fixes.",
                "10:00 AM - 6:00 PM", R.drawable.ic_avatar_3));

        providers.add(new Provider(7, "CoolCare AC Services", "AC Repair",
                4.9f, 8, 2000,
                "AC installation, gas refill, cleaning, and cooling system repairs.",
                "9:00 AM - 8:00 PM", R.drawable.ic_avatar_1));

        providers.add(new Provider(8, "ChillTech AC Experts", "AC Repair",
                4.6f, 4, 1900,
                "Split and window AC servicing with genuine spare parts.",
                "10:00 AM - 9:00 PM", R.drawable.ic_avatar_2));

        providers.add(new Provider(9, "FrostLine Cooling", "AC Repair",
                4.8f, 10, 2200,
                "Commercial and home AC maintenance with same-day slots.",
                "8:00 AM - 8:00 PM", R.drawable.ic_avatar_3));

        providers.add(new Provider(10, "Sparkle Home Cleaning", "Home Cleaning",
                4.7f, 4, 1800,
                "Professional deep cleaning for homes, kitchens, and bathrooms.",
                "8:00 AM - 5:00 PM", R.drawable.ic_avatar_1));

        providers.add(new Provider(11, "NeatNest Cleaners", "Home Cleaning",
                4.5f, 5, 1600,
                "Move-in cleaning, sofa shampoo, and weekly home tidy packages.",
                "9:00 AM - 6:00 PM", R.drawable.ic_avatar_2));

        providers.add(new Provider(12, "GlowHome Services", "Home Cleaning",
                4.4f, 3, 1500,
                "Eco-friendly cleaning products and trained residential teams.",
                "8:00 AM - 4:00 PM", R.drawable.ic_avatar_3));

        providers.add(new Provider(13, "Perfect Paint Services", "Painting",
                4.5f, 6, 2500,
                "Interior and exterior painting with neat finishing and quality materials.",
                "10:00 AM - 6:00 PM", R.drawable.ic_avatar_1));

        providers.add(new Provider(14, "ColorCraft Painters", "Painting",
                4.8f, 8, 2800,
                "Texture walls, weather coating, and colour consultation included.",
                "9:00 AM - 7:00 PM", R.drawable.ic_avatar_2));

        providers.add(new Provider(15, "Brush & Roll Pros", "Painting",
                4.3f, 4, 2200,
                "Apartment and villa painting with tidy site protection.",
                "10:00 AM - 5:00 PM", R.drawable.ic_avatar_3));

        return providers;
    }

    public static List<Booking> getSampleBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(
                1,
                "Electrician",
                "Ahmed Electric Services",
                "20 Oct 2026",
                "11:00 AM",
                "House 12, Gulberg III, Lahore",
                "Fan not working",
                1500,
                Booking.STATUS_CONFIRMED
        ));
        bookings.add(new Booking(
                2,
                "Home Cleaning",
                "Sparkle Home Cleaning",
                "10 Oct 2026",
                "2:00 PM",
                "Flat 5, DHA Phase 5, Karachi",
                "",
                1800,
                Booking.STATUS_COMPLETED
        ));
        return bookings;
    }
}
