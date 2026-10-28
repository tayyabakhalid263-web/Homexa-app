package com.homexa.app.models;

/**
 * One service provider.
 * Example: Ahmed Electric Services.
 */
public class Provider {

    private final int id;
    private final String name;
    private final String category;
    private final float rating;
    private final int experienceYears;
    private final int startingPrice;
    private final String description;
    private final String availableTime;
    private final int imageResId;

    public Provider(
            int id,
            String name,
            String category,
            float rating,
            int experienceYears,
            int startingPrice,
            String description,
            String availableTime,
            int imageResId
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.experienceYears = experienceYears;
        this.startingPrice = startingPrice;
        this.description = description;
        this.availableTime = availableTime;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getRating() {
        return rating;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public int getImageResId() {
        return imageResId;
    }
}
