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

class DbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Tasks.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_DISH_CREATE_ENTRIES =
            "CREATE TABLE " + DishesPersistenceContract.DishEntry.TABLE_NAME + " (" +
                    DishesPersistenceContract.DishEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    DishesPersistenceContract.DishEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.DishEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.DishEntry.COLUMN_NAME_SIDE_DISH_ID + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID + TEXT_TYPE +
            " )";

    private static final String SQL_SIDEDISH_CREATE_ENTRIES =
            "CREATE TABLE " + DishesPersistenceContract.SideDishEntry.TABLE_NAME + " (" +
                    DishesPersistenceContract.SideDishEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    DishesPersistenceContract.SideDishEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.SideDishEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.SideDishEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DishesPersistenceContract.SideDishEntry.COLUMN_NAME_IS_MIXTURE + BOOLEAN_TYPE +
                    " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating all necessary tables...");

        db.execSQL(SQL_DISH_CREATE_ENTRIES);
        db.execSQL(SQL_SIDEDISH_CREATE_ENTRIES);
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
