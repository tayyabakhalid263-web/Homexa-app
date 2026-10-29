package com.homexa.app.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Local login / signup using SQLite + SharedPreferences session.
 *
 * Teacher explanation:
 * - DatabaseHelper users table mein account save hota hai
 * - SharedPreferences mein "current user id" rakha jata hai (session)
 * - App band hone ke baad bhi user logged-in reh sakta hai
 */
public final class AuthHelper {

    private static final String PREFS = "homexa_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";

    public interface AuthCallback {
        void onSuccess(long userId);

        void onError(String message);
    }

    private AuthHelper() {
    }

    public static boolean isLoggedIn(Context context) {
        return getCurrentUserId(context) > 0;
    }

    public static long getCurrentUserId(Context context) {
        return prefs(context).getLong(KEY_USER_ID, -1);
    }

    public static String getCurrentUserName(Context context) {
        return prefs(context).getString(KEY_USER_NAME, "");
    }

    /** Create account in SQLite and start session. */
    public static void signUp(
            Context context,
            String name,
            String email,
            String phone,
            String password,
            AuthCallback callback
    ) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        long id = db.registerUser(name, email, phone, password);
        if (id == -1) {
            callback.onError("This email is already registered. Please login.");
            return;
        }
        saveSession(context, id, name, email);
        callback.onSuccess(id);
    }

    /** Check email/password in SQLite and start session. */
    public static void signIn(Context context, String email, String password, AuthCallback callback) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        long id = db.loginUser(email, password);
        if (id == -1) {
            callback.onError("Wrong email or password.");
            return;
        }
        String name = db.getUserName(id);
        saveSession(context, id, name != null ? name : "", email);
        callback.onSuccess(id);
    }

    public static void signOut(Context context) {
        prefs(context).edit().clear().apply();
    }

    private static void saveSession(Context context, long userId, String name, String email) {
        prefs(context).edit()
                .putLong(KEY_USER_ID, userId)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_EMAIL, email)
                .apply();
    }

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
