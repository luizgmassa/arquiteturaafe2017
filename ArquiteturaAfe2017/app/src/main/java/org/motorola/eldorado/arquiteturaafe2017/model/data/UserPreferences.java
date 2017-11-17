package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class used to store / retrieve user preferences.
 */
public class UserPreferences {

    /**
     * Holds the Shared Preferences key for User.
     */
    private static final String PREFS_STR_USER = "user";

    /**
     * Holds the Shared Preferences name.
     */
    private static final String PREFS_NAME = "quedlcprefs";

    /**
     * Holds a instance for this class.
     */
    private static UserPreferences mInstance;

    /**
     * Holds the Shared Preferences instance.
     */
    private SharedPreferences mSharedPrefs;

    /**
     * Private constructor that prevents direct instantiation.
     *
     * @param ctx the context.
     */
    private UserPreferences(@NonNull Context ctx) {
        Context context = checkNotNull(ctx);
        mSharedPrefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    /**
     * Get a instance for User Preferences.
     *
     * @param context the context.
     * @return a instance for User Preferences.
     */
    public static UserPreferences getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new UserPreferences(context);
        }

        return mInstance;
    }

    /**
     * Gets the user string.
     *
     * @return the user string.
     */
    @Nullable
    public String getUser() {
        return mSharedPrefs.getString(PREFS_STR_USER, null);
    }

    /**
     * Sets the user string.
     *
     * @param user the user string.
     */
    public void setUser(@NonNull String user) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(PREFS_STR_USER, user);
        editor.apply();
    }
}
