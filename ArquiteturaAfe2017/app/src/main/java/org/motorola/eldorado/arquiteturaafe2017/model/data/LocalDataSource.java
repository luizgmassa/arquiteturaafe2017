package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DishesPersistenceContract.DishEntry;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DishesPersistenceContract.SideDishEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class LocalDataSource implements DishesDataSource {

    private static final String LOG_TAG = LocalDataSource.class.getSimpleName();

    private static LocalDataSource mInstance;

    private final DbHelper mDbHelper;

    // Prevent direct instantiation.
    private LocalDataSource(@NonNull Context context) {
        Context ctx = checkNotNull(context);
        mDbHelper = new DbHelper(ctx);

        this.fillDishes(ctx);
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new LocalDataSource(context);
        }
        return mInstance;
    }

    @Override
    public void getDishes(@NonNull LoadDishesCallback callback) {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DishEntry.COLUMN_NAME_ENTRY_ID,
                DishEntry.COLUMN_NAME_NAME,
                DishEntry.COLUMN_NAME_DESCRIPTION,
                DishEntry.COLUMN_NAME_IMAGE_NAME,
                DishEntry.COLUMN_NAME_SIDE_DISH_ID,
                DishEntry.COLUMN_NAME_MIXTURE_ID
        };

        Cursor c = db.query(
                DishEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Dish dish = getDishFromCursor(c);

                dishes.add(dish);

                Log.d(LOG_TAG, "Adding 1 dish to list!!");
            }

            c.close();
        }

        db.close();

        if (dishes.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onDishesLoaded(dishes);
        }
    }

    @Override
    public void getDish(@NonNull String dishId, @NonNull GetDishCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DishEntry.COLUMN_NAME_ENTRY_ID,
                DishEntry.COLUMN_NAME_NAME,
                DishEntry.COLUMN_NAME_DESCRIPTION,
                DishEntry.COLUMN_NAME_IMAGE_NAME,
                DishEntry.COLUMN_NAME_SIDE_DISH_ID,
                DishEntry.COLUMN_NAME_MIXTURE_ID
        };

        String selection = DishEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { dishId };

        Cursor c = db.query(
                DishEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Dish dish = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            dish = getDishFromCursor(c);

            c.close();
        }

        db.close();

        if (dish != null) {
            callback.onDishLoaded(dish);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void fillDishes(Context context) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values;

        if (!isTableEmpty(db, DishEntry.TABLE_NAME)) {
            Log.d(LOG_TAG, "No need to insert again");
            return;
        }

        String currentLine;
        int i = 0;
        String[] lineValues;
        AssetManager assets = context.getAssets();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("dishes.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(DishEntry.COLUMN_NAME_ENTRY_ID, i);
                values.put(DishEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(DishEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);
                values.put(DishEntry.COLUMN_NAME_IMAGE_NAME, lineValues[2]);
                values.put(DishEntry.COLUMN_NAME_SIDE_DISH_ID, lineValues[3]);
                values.put(DishEntry.COLUMN_NAME_MIXTURE_ID, lineValues[4]);

                db.insert(DishEntry.TABLE_NAME, null, values);
                i++;
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("side_dishes.info")))) {
            i = 0;

            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(SideDishEntry.COLUMN_NAME_ENTRY_ID, i);
                values.put(SideDishEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(SideDishEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);
                values.put(SideDishEntry.COLUMN_NAME_IS_MIXTURE, lineValues[2]);

                db.insert(SideDishEntry.TABLE_NAME, null, values);
                i++;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        db.close();
    }

    private boolean isTableEmpty(SQLiteDatabase db, String tableName) {
        String query = "SELECT count(*) FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();

        return count <= 0;
    }

    private Dish getDishFromCursor(Cursor c) {
        ArrayList<SideDish> sideDishes = new ArrayList<>();
        ArrayList<SideDish> mixtures = new ArrayList<>();

        String itemId = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_ENTRY_ID));
        String name = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_DESCRIPTION));
        String imageName = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_IMAGE_NAME));
        String sideDishesIds = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_SIDE_DISH_ID));
        String mixturesIds = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_MIXTURE_ID));

        String[] sideDishesIdsSplitted = sideDishesIds.split(",");
        String[] mixturesIdsSplitted = mixturesIds.split(",");

        sideDishes.addAll(getSideDishes(sideDishesIdsSplitted));
        mixtures.addAll(getSideDishes(mixturesIdsSplitted));

        return new Dish(itemId, name, description, imageName, sideDishes, mixtures);
    }

    private List<SideDish> getSideDishes(String[] sideDishes) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<SideDish> sideDishesList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + SideDishEntry.TABLE_NAME
                    + " WHERE " + SideDishEntry.COLUMN_NAME_ENTRY_ID + " IN (" + makePlaceholders(sideDishes.length) + ")";
            Cursor c = db.rawQuery(query, sideDishes);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String itemId = c.getString(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_ENTRY_ID));
                    String name = c.getString(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_NAME));
                    String description = c.getString(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_DESCRIPTION));
                    boolean isMixture = c.getInt(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_IS_MIXTURE)) == 1;

                    SideDish sideDish = new SideDish(itemId, name, description, isMixture);

                    sideDishesList.add(sideDish);
                }

                c.close();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return sideDishesList;
    }

    private String makePlaceholders(int len) throws Exception {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new Exception("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");

            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }

            return sb.toString();
        }
    }
}
