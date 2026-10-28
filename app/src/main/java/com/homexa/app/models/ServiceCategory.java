package com.homexa.app.models;

/**
 * One service category shown on the Home screen.
 * Example: Electrician, Plumber, Cleaning.
 */
public class ServiceCategory {

    private final String name;
    private final int iconResId;

    public ServiceCategory(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
