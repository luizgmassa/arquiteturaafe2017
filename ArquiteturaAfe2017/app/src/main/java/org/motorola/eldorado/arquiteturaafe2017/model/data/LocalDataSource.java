package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.DishSize;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.PersistenceContract.DishEntry;
import org.motorola.eldorado.arquiteturaafe2017.model.data.PersistenceContract.DrinkEntry;
import org.motorola.eldorado.arquiteturaafe2017.model.data.PersistenceContract.MixtureEntry;
import org.motorola.eldorado.arquiteturaafe2017.model.data.PersistenceContract.SideDishEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Local Data Source class.
 */
public class LocalDataSource implements DataSource {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = LocalDataSource.class.getSimpleName();

    /**
     * Holds a instance for this class.
     */
    private static LocalDataSource mInstance;

    /**
     * Holds the Database Helper instance.
     */
    private final DbHelper mDbHelper;

    /**
     * Private constructor that prevents direct instantiation.
     *
     * @param context the context.
     */
    private LocalDataSource(@NonNull Context context) {
        Context ctx = checkNotNull(context);
        mDbHelper = new DbHelper(ctx);

        this.fillDishes(ctx);
        this.fillDrinks(ctx);
    }

    /**
     * Get a instance for this Local Data Source.
     *
     * @param context the context.
     * @return a instance for the Local Data Source.
     */
    public static LocalDataSource getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new LocalDataSource(context);
        }

        return mInstance;
    }

    @Override
    public void getAllInfo(@NonNull LoadAllInfoCallback callback) {
        List<Dish> dishes = new ArrayList<>();
        List<SideDish> sideDishes = new ArrayList<>();
        List<Mixture> mixtures = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projectionSideDishes = {
                SideDishEntry.COLUMN_NAME_ENTRY_ID,
                SideDishEntry.COLUMN_NAME_NAME,
                SideDishEntry.COLUMN_NAME_DESCRIPTION
        };

        String[] projectionMixture = {
                MixtureEntry.COLUMN_NAME_ENTRY_ID,
                MixtureEntry.COLUMN_NAME_NAME,
                MixtureEntry.COLUMN_NAME_DESCRIPTION
        };

        String[] projection = {
                DishEntry.COLUMN_NAME_ENTRY_ID,
                DishEntry.COLUMN_NAME_NAME,
                DishEntry.COLUMN_NAME_DESCRIPTION,
                DishEntry.COLUMN_NAME_DISH_SIZE,
                DishEntry.COLUMN_NAME_IMAGE_NAME,
                DishEntry.COLUMN_NAME_MIXTURE_ID
        };

        Cursor c = db.query(SideDishEntry.TABLE_NAME, projectionSideDishes, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SideDish sideDish = getSideDishFromCursor(c);

                sideDishes.add(sideDish);
            }

            c.close();
        }

        c = db.query(MixtureEntry.TABLE_NAME, projectionMixture, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Mixture mixture = getMixtureFromCursor(c);

                mixtures.add(mixture);
            }

            c.close();
        }

        c = db.query(DishEntry.TABLE_NAME, projection, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Dish dish = getDishFromCursor(c);

                dishes.add(dish);
            }

            c.close();
        }

        db.close();

        if (dishes.isEmpty() || sideDishes.isEmpty() || mixtures.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onDishesLoaded(dishes, sideDishes, mixtures);
        }
    }

    @Override
    public void getDishes(@NonNull LoadDishesCallback callback) {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DishEntry.COLUMN_NAME_ENTRY_ID,
                DishEntry.COLUMN_NAME_NAME,
                DishEntry.COLUMN_NAME_DESCRIPTION,
                DishEntry.COLUMN_NAME_DISH_SIZE,
                DishEntry.COLUMN_NAME_IMAGE_NAME,
                DishEntry.COLUMN_NAME_MIXTURE_ID,
                DishEntry.COLUMN_NAME_SIDE_DISH_ID
        };

        Cursor c = db.query(
                DishEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Dish dish = getCompleteDishFromCursor(c);

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
    public void getDrinks(@NonNull LoadDrinksCallback callback) {
        List<Drink> drinks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DrinkEntry.COLUMN_NAME_ENTRY_ID,
                DrinkEntry.COLUMN_NAME_NAME,
                DrinkEntry.COLUMN_NAME_DESCRIPTION,
                DrinkEntry.COLUMN_NAME_IMAGE_NAME
        };

        Cursor c = db.query(
                DrinkEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Drink drink = getDrinkFromCursor(c);

                drinks.add(drink);

                Log.d(LOG_TAG, "Adding 1 drink to list!!");
            }

            c.close();
        }

        db.close();

        if (drinks.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onDrinksLoaded(drinks);
        }
    }

    @Override
    public void getDish(@NonNull String dishId, @NonNull GetDishCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DishEntry.COLUMN_NAME_ENTRY_ID,
                DishEntry.COLUMN_NAME_NAME,
                DishEntry.COLUMN_NAME_DESCRIPTION,
                DishEntry.COLUMN_NAME_DISH_SIZE,
                DishEntry.COLUMN_NAME_IMAGE_NAME,
                DishEntry.COLUMN_NAME_MIXTURE_ID,
                DishEntry.COLUMN_NAME_SIDE_DISH_ID
        };

        String selection = DishEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { dishId };

        Cursor c = db.query(
                DishEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Dish dish = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            dish = getCompleteDishFromCursor(c);

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

        if (!isTableEmpty(db, PersistenceContract.DishEntry.TABLE_NAME)) {
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
                values.put(DishEntry.COLUMN_NAME_DISH_SIZE, lineValues[2]);
                values.put(DishEntry.COLUMN_NAME_IMAGE_NAME, lineValues[3]);
                values.put(DishEntry.COLUMN_NAME_MIXTURE_ID, lineValues[4]);
                values.put(DishEntry.COLUMN_NAME_SIDE_DISH_ID, lineValues[5]);

                db.insert(DishEntry.TABLE_NAME, null, values);
                i++;
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("mixtures.info")))) {
            i = 0;

            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(MixtureEntry.COLUMN_NAME_ENTRY_ID, i);
                values.put(MixtureEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(MixtureEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);

                db.insert(MixtureEntry.TABLE_NAME, null, values);
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

                db.insert(SideDishEntry.TABLE_NAME, null, values);
                i++;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        db.close();
    }

    @Override
    public void fillDrinks(Context context) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values;

        if (!isTableEmpty(db, DrinkEntry.TABLE_NAME)) {
            Log.d(LOG_TAG, "No need to insert again");
            return;
        }

        String currentLine;
        int i = 0;
        String[] lineValues;
        AssetManager assets = context.getAssets();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("drinks.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(DrinkEntry.COLUMN_NAME_ENTRY_ID, i);
                values.put(DrinkEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(DrinkEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);
                values.put(DrinkEntry.COLUMN_NAME_IMAGE_NAME, lineValues[2]);

                db.insert(DrinkEntry.TABLE_NAME, null, values);
                i++;
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        db.close();
    }

    /**
     * Verifies if a table is empty.
     *
     * @param db the SQLite database object.
     * @param tableName the table name.
     * @return true if it's empty, otherwise false.
     */
    private boolean isTableEmpty(SQLiteDatabase db, String tableName) {
        String query = "SELECT count(*) FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();

        return count <= 0;
    }

    /**
     * Gets a complete dish object from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the dish object retrived from the cursor database.
     */
    private Dish getCompleteDishFromCursor(Cursor c) {
        ArrayList<SideDish> sideDishes = new ArrayList<>();

        Dish defaultDish = getDishFromCursor(c);

        String sideDishesIds = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_SIDE_DISH_ID));
        String[] sideDishesIdsSplitted = sideDishesIds.split(",");
        sideDishes.addAll(getSideDishesFromIds(sideDishesIdsSplitted));

        return new Dish(defaultDish.getId(), defaultDish.getName(), defaultDish.getDescription(),
                defaultDish.getSize(), defaultDish.getImageName(), sideDishes, defaultDish.getMixture());
    }

    /**
     * Gets a dish from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the dish object retrieved from the cursor database.
     */
    private Dish getDishFromCursor(Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_ENTRY_ID));
        String name = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_DESCRIPTION));
        DishSize size = DishSize.valueOf(c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_DISH_SIZE)));
        String imageName = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_IMAGE_NAME));

        String mixtureId = c.getString(c.getColumnIndexOrThrow(DishEntry.COLUMN_NAME_MIXTURE_ID));
        Mixture mixture = getMixtureFromId(mixtureId);

        return new Dish(itemId, name, description, size, imageName, mixture);
    }

    /**
     * Gets a side dish from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the side dish object retrieved from the cursor database.
     */
    private SideDish getSideDishFromCursor(Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_ENTRY_ID));
        String name = c.getString(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(SideDishEntry.COLUMN_NAME_DESCRIPTION));

        return new SideDish(itemId, name, description);
    }

    /**
     * Gets a mixture from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the mixture object retrieved from the cursor database.
     */
    private Mixture getMixtureFromCursor(Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(MixtureEntry.COLUMN_NAME_ENTRY_ID));
        String name = c.getString(c.getColumnIndexOrThrow(MixtureEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(MixtureEntry.COLUMN_NAME_DESCRIPTION));

        return new Mixture(itemId, name, description);
    }

    /**
     * Gets a drink from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the drink object retrieved from the cursor database.
     */
    private Drink getDrinkFromCursor(Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(DrinkEntry.COLUMN_NAME_ENTRY_ID));
        String name = c.getString(c.getColumnIndexOrThrow(DrinkEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(DrinkEntry.COLUMN_NAME_DESCRIPTION));
        String imageName = c.getString(c.getColumnIndexOrThrow(DrinkEntry.COLUMN_NAME_IMAGE_NAME));

        return new Drink(itemId, name, description, imageName);
    }

    /**
     * Gets the mixture from a dish.
     *
     * @param id the mixture id.
     * @return the mixture object from a dish.
     */
    private Mixture getMixtureFromId(String id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Mixture mixture = null;

        try {
            String query = "SELECT * FROM " + MixtureEntry.TABLE_NAME
                    + " WHERE " + MixtureEntry.COLUMN_NAME_ENTRY_ID + " = '" + id + "'";
            Cursor c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {
                if (c.moveToNext()) {
                    mixture = getMixtureFromCursor(c);
                }

                c.close();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return mixture;
    }

    /**
     * Gets a list of side dishes.
     *
     * @param sideDishes the side dishes array ids.
     * @return the list of side dishes objects retrieved from data base.
     */
    private List<SideDish> getSideDishesFromIds(String[] sideDishes) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<SideDish> sideDishesList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + SideDishEntry.TABLE_NAME
                    + " WHERE " + SideDishEntry.COLUMN_NAME_ENTRY_ID + " IN (" + makePlaceholders(sideDishes.length) + ")";
            Cursor c = db.rawQuery(query, sideDishes);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    SideDish sideDish = getSideDishFromCursor(c);

                    sideDishesList.add(sideDish);
                }

                c.close();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return sideDishesList;
    }

    /**
     * Inserts placeholders into a string.
     *
     * @param len the number of placeholders.
     * @return a string with all needed placeholders.
     * @throws Exception if something goes wrong with string builder.
     */
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
