/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Database Helper class.
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
     * Holds the SQL query for Dish table creation.
     */
    private static final String SQL_DISH_CREATE_ENTRIES =
            "CREATE TABLE " + LocalPersistenceContract.DishEntry.DISH_TABLE_NAME + " (" +
                    LocalPersistenceContract.DishEntry.DISH_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
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
            "CREATE TABLE " + LocalPersistenceContract.SideDishEntry.SIDE_DISH_TABLE_NAME + " (" +
                    LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_DESCRIPTION + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Mixture table creation.
     */
    private static final String SQL_MIXTURE_CREATE_ENTRIES =
            "CREATE TABLE " + LocalPersistenceContract.MixtureEntry.MIXTURE_TABLE_NAME + " (" +
                    LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_DESCRIPTION + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_DRINKS_CREATE_ENTRIES =
            "CREATE TABLE " + LocalPersistenceContract.DrinkEntry.DRINK_TABLE_NAME + " (" +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_IMAGE_NAME + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_HISTORY_CREATE_ENTRIES =
            "CREATE TABLE " + LocalPersistenceContract.HistoryEntry.HISTORY_TABLE_NAME + " (" +
                    LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
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
