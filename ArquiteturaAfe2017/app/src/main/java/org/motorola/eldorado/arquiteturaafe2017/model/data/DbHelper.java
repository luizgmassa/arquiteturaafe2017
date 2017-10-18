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
     * Holds the string for Boolean type.
     */
    private static final String BOOLEAN_TYPE = " INTEGER";

    /**
     * Holds the string for comma separation.
     */
    private static final String COMMA_SEP = ",";

    /**
     * Holds the SQL query for Dish table creation.
     */
    private static final String SQL_DISH_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.DishEntry.TABLE_NAME + " (" +
                    PersistenceContract.DishEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PersistenceContract.DishEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.COLUMN_NAME_DISH_SIZE + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.COLUMN_NAME_SIDE_DISH_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID + TEXT_TYPE +
            " )";

    /**
     * Holds the SQL query for Side Dish table creation.
     */
    private static final String SQL_SIDEDISH_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.SideDishEntry.TABLE_NAME + " (" +
                    PersistenceContract.SideDishEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PersistenceContract.SideDishEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.SideDishEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.SideDishEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.SideDishEntry.COLUMN_NAME_IS_MIXTURE + BOOLEAN_TYPE +
                    " )";

    /**
     * Holds the SQL query for Drinks table creation.
     */
    private static final String SQL_DRINKS_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.DrinkEntry.TABLE_NAME + " (" +
                    PersistenceContract.DrinkEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PersistenceContract.DrinkEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DrinkEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DrinkEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.DrinkEntry.COLUMN_NAME_IMAGE_NAME + TEXT_TYPE +
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
        db.execSQL(SQL_SIDEDISH_CREATE_ENTRIES);
        db.execSQL(SQL_DRINKS_CREATE_ENTRIES);
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
