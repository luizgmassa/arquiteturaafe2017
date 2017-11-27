package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Local Database Helper class.
 */
class LocalDbHelper extends SQLiteOpenHelper {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = LocalDbHelper.class.getSimpleName();

    /**
     * Holds the Database version for this app version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Holds the Database file name.
     */
    private static final String DATABASE_NAME = "Dishes.db";

    /**
     * Holds the string for Text type.
     */
    private static final String TEXT_TYPE = " TEXT";

    /**
     * Holds the string for Float type.
     */
    private static final String FLOAT_TYPE = " REAL";

    /**
     * Holds the string for Integer type.
     */
    private static final String INTEGER_TYPE = " INTEGER";

    /**
     * Holds the string for comma separation.
     */
    private static final String COMMA_SEP = ",";

    /**
     * Holds the constant for Databse Create Table string.
     */
    private static final String STRING_DB_CREATE_TABLE = "CREATE TABLE ";

    /**
     * Holds the constant for Databse Primary Key Auto Increment string.
     */
    private static final String STRING_DB_PRIMARY_KEY_AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT,";

    /**
     * Holds the SQL query for Dish table creation.
     */
    private static final String SQL_DISH_CREATE_ENTRIES =
            STRING_DB_CREATE_TABLE + LocalPersistenceContract.DishEntry.DISH_TABLE_NAME + " (" +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_ID + INTEGER_TYPE + STRING_DB_PRIMARY_KEY_AUTOINCREMENT +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_SIZE + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_SIDE_DISH_ID + TEXT_TYPE +
            " )";

    /**
     * Holds the SQL query for Side Dish table creation.
     */
    private static final String SQL_SIDE_DISH_CREATE_ENTRIES =
            STRING_DB_CREATE_TABLE + LocalPersistenceContract.SideDishEntry.SIDE_DISH_TABLE_NAME + " (" +
                    LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_ID + INTEGER_TYPE + STRING_DB_PRIMARY_KEY_AUTOINCREMENT +
                    LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_DESCRIPTION + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Mixture table creation.
     */
    private static final String SQL_MIXTURE_CREATE_ENTRIES =
            STRING_DB_CREATE_TABLE + LocalPersistenceContract.MixtureEntry.MIXTURE_TABLE_NAME + " (" +
                    LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_ID + INTEGER_TYPE + STRING_DB_PRIMARY_KEY_AUTOINCREMENT +
                    LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_DESCRIPTION + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_DRINKS_CREATE_ENTRIES =
            STRING_DB_CREATE_TABLE + LocalPersistenceContract.DrinkEntry.DRINK_TABLE_NAME + " (" +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_ID + INTEGER_TYPE + STRING_DB_PRIMARY_KEY_AUTOINCREMENT +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_IMAGE_NAME + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_HISTORY_CREATE_ENTRIES =
            STRING_DB_CREATE_TABLE + LocalPersistenceContract.HistoryEntry.HISTORY_TABLE_NAME + " (" +
                    LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_ID + INTEGER_TYPE + STRING_DB_PRIMARY_KEY_AUTOINCREMENT +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_ID + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_SIZE + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_SIDE_DISH_ID + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DISH_COLUMN_MIXTURE_ID + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_ID + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_PAYMENT_METHOD + TEXT_TYPE +
                    " )";

    /**
     * Constructor.
     *
     * @param context the context.
     */
    LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating all necessary tables...");

        db.execSQL(SQL_DISH_CREATE_ENTRIES);
        db.execSQL(SQL_SIDE_DISH_CREATE_ENTRIES);
        db.execSQL(SQL_MIXTURE_CREATE_ENTRIES);
        db.execSQL(SQL_DRINKS_CREATE_ENTRIES);
        db.execSQL(SQL_HISTORY_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
