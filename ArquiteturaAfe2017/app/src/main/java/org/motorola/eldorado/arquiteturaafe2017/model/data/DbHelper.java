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
class DbHelper extends SQLiteOpenHelper {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = DbHelper.class.getSimpleName();

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
            "CREATE TABLE " + PersistenceContract.DishEntry.DISH_TABLE_NAME + " (" +
                    PersistenceContract.DishEntry.DISH_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    PersistenceContract.DishEntry.DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.DISH_COLUMN_SIZE + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.DISH_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.DISH_COLUMN_SIDE_DISH_ID + TEXT_TYPE +
            " )";

    /**
     * Holds the SQL query for Side Dish table creation.
     */
    private static final String SQL_SIDE_DISH_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.SideDishEntry.SIDE_DISH_TABLE_NAME + " (" +
                    PersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    PersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_DESCRIPTION + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Mixture table creation.
     */
    private static final String SQL_MIXTURE_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.MixtureEntry.MIXTURE_TABLE_NAME + " (" +
                    PersistenceContract.MixtureEntry.MIXTURE_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    PersistenceContract.MixtureEntry.MIXTURE_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.MixtureEntry.MIXTURE_COLUMN_DESCRIPTION + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_DRINKS_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.DrinkEntry.DRINK_TABLE_NAME + " (" +
                    PersistenceContract.DrinkEntry.DRINK_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    PersistenceContract.DrinkEntry.DRINK_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DrinkEntry.DRINK_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DrinkEntry.DRINK_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    PersistenceContract.DrinkEntry.DRINK_COLUMN_IMAGE_NAME + TEXT_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_HISTORY_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.HistoryEntry.HISTORY_TABLE_NAME + " (" +
                    PersistenceContract.HistoryEntry.HISTORY_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_SIZE + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_SIDE_DISH_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DISH_COLUMN_MIXTURE_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DRINK_COLUMN_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DRINK_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DRINK_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DRINK_COLUMN_PRICE + FLOAT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.DRINK_COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.HistoryEntry.HISTORY_COLUMN_PAYMENT_METHOD + TEXT_TYPE +
                    " )";

    /**
     * Constructor.
     *
     * @param context the context.
     */
    DbHelper(Context context) {
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
