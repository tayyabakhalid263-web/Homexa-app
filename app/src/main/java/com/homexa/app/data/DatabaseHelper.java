package com.homexa.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.homexa.app.models.Booking;
import com.homexa.app.models.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Local SQLite database for Homexa.
 *
 * Teacher explanation:
 * - SQLite = phone pe stored database (internet ki zaroorat nahi)
 * - 3 tables: users, providers, bookings
 * - SQLiteOpenHelper tables create/update karta hai
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "homexa.db";
    private static final int DB_VERSION = 1;

    // ---- table names ----
    public static final String TABLE_USERS = "users";
    public static final String TABLE_PROVIDERS = "providers";
    public static final String TABLE_BOOKINGS = "bookings";

    private static DatabaseHelper instance;

    /** One shared database helper for the whole app. */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table — Sign Up / Login
        db.execSQL(
                "CREATE TABLE " + TABLE_USERS + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "name TEXT NOT NULL, "
                        + "email TEXT NOT NULL UNIQUE, "
                        + "phone TEXT, "
                        + "password TEXT NOT NULL)"
        );

        // Providers table — service workers list
        db.execSQL(
                "CREATE TABLE " + TABLE_PROVIDERS + " ("
                        + "id INTEGER PRIMARY KEY, "
                        + "name TEXT NOT NULL, "
                        + "category TEXT NOT NULL, "
                        + "rating REAL, "
                        + "experience_years INTEGER, "
                        + "starting_price INTEGER, "
                        + "description TEXT, "
                        + "available_time TEXT, "
                        + "image_res_id INTEGER)"
        );

        // Bookings table — linked to a user with user_id
        db.execSQL(
                "CREATE TABLE " + TABLE_BOOKINGS + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "user_id INTEGER NOT NULL, "
                        + "service_category TEXT, "
                        + "provider_name TEXT, "
                        + "date TEXT, "
                        + "time TEXT, "
                        + "address TEXT, "
                        + "note TEXT, "
                        + "estimated_price INTEGER, "
                        + "status TEXT, "
                        + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple student approach: drop and recreate if version changes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVIDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ===================== USERS (Auth) =====================

    /** Sign Up — insert new user. Returns new user id, or -1 if email already exists. */
    public long registerUser(String name, String email, String phone, String password) {
        if (emailExists(email)) {
            return -1; // email already registered
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email.trim().toLowerCase());
        values.put("phone", phone);
        values.put("password", password); // learning project: plain text (not for production)
        return db.insert(TABLE_USERS, null, values);
    }

    /**
     * Login check — email + password match.
     * Returns user id if OK, or -1 if wrong.
     */
    public long loginUser(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{"id", "password"},
                "email = ?",
                new String[]{email.trim().toLowerCase()},
                null, null, null
        );
        long userId = -1;
        if (cursor.moveToFirst()) {
            String stored = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            if (stored.equals(password)) {
                userId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            }
        }
        cursor.close();
        return userId;
    }

    /** Returns name for a user id (for welcome messages). */
    public String getUserName(long userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{"name"},
                "id = ?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );
        String name = null;
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }

    private boolean emailExists(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{"id"},
                "email = ?",
                new String[]{email.trim().toLowerCase()},
                null, null, null
        );
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    // ===================== PROVIDERS =====================

    /** First launch: copy SampleData providers into SQLite once. */
    public void seedProvidersIfEmpty() {
        if (countProviders() > 0) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Provider p : SampleData.getProviders()) {
                ContentValues values = new ContentValues();
                values.put("id", p.getId());
                values.put("name", p.getName());
                values.put("category", p.getCategory());
                values.put("rating", p.getRating());
                values.put("experience_years", p.getExperienceYears());
                values.put("starting_price", p.getStartingPrice());
                values.put("description", p.getDescription());
                values.put("available_time", p.getAvailableTime());
                values.put("image_res_id", p.getImageResId());
                db.insert(TABLE_PROVIDERS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private int countProviders() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PROVIDERS, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public List<Provider> getAllProviders() {
        List<Provider> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROVIDERS, null, null, null, null, null, "id ASC");
        while (cursor.moveToNext()) {
            list.add(readProvider(cursor));
        }
        cursor.close();
        return list;
    }

    private Provider readProvider(Cursor cursor) {
        return new Provider(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("category")),
                cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
                cursor.getInt(cursor.getColumnIndexOrThrow("experience_years")),
                cursor.getInt(cursor.getColumnIndexOrThrow("starting_price")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getString(cursor.getColumnIndexOrThrow("available_time")),
                cursor.getInt(cursor.getColumnIndexOrThrow("image_res_id"))
        );
    }

    // ===================== BOOKINGS =====================

    /** Save a booking for the logged-in user. Returns new row id. */
    public long insertBooking(long userId, Booking booking) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("service_category", booking.getServiceCategory());
        values.put("provider_name", booking.getProviderName());
        values.put("date", booking.getDate());
        values.put("time", booking.getTime());
        values.put("address", booking.getAddress());
        values.put("note", booking.getNote());
        values.put("estimated_price", booking.getEstimatedPrice());
        values.put("status", booking.getStatus());
        return db.insert(TABLE_BOOKINGS, null, values);
    }

    /** Load all bookings for one user (newest first). */
    public List<Booking> getBookingsForUser(long userId) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_BOOKINGS,
                null,
                "user_id = ?",
                new String[]{String.valueOf(userId)},
                null, null,
                "id DESC"
        );
        while (cursor.moveToNext()) {
            list.add(readBooking(cursor));
        }
        cursor.close();
        return list;
    }

    private Booking readBooking(Cursor cursor) {
        return new Booking(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("service_category")),
                cursor.getString(cursor.getColumnIndexOrThrow("provider_name")),
                cursor.getString(cursor.getColumnIndexOrThrow("date")),
                cursor.getString(cursor.getColumnIndexOrThrow("time")),
                cursor.getString(cursor.getColumnIndexOrThrow("address")),
                cursor.getString(cursor.getColumnIndexOrThrow("note")),
                cursor.getInt(cursor.getColumnIndexOrThrow("estimated_price")),
                cursor.getString(cursor.getColumnIndexOrThrow("status"))
        );
    }
}
