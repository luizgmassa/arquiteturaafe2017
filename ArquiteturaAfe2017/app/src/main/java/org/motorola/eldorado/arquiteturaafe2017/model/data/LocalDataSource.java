package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.DishSize;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Local Data Source class.
 */
public class LocalDataSource implements LocalData {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = LocalDataSource.class.getSimpleName();

    /**
     * Holds the constant for Error reading files string.
     */
    private static final String STRING_ERROR_WHEN_READING_FILES = "Error when reading files: ";

    /**
     * Holds a instance for this class.
     */
    private static LocalDataSource mInstance;

    /**
     * Holds the Database Helper instance.
     */
    private final LocalDbHelper mLocalDbHelper;

    /**
     * Private constructor that prevents direct instantiation.
     *
     * @param context the context.
     */
    private LocalDataSource(@NonNull Context context) {
        Context ctx = checkNotNull(context);
        mLocalDbHelper = new LocalDbHelper(ctx);

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
        boolean failure = false;
        SQLiteDatabase db = mLocalDbHelper.getReadableDatabase();

        String[] projectionSideDishes = {
                LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_ID,
                LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_NAME,
                LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_DESCRIPTION
        };

        String[] projectionMixture = {
                LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_ID,
                LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_NAME,
                LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_DESCRIPTION
        };

        String[] projection = {
                LocalPersistenceContract.DishEntry.DISH_COLUMN_ID,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_NAME,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_SIZE,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_PRICE,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID
        };

        Cursor c = db.query(LocalPersistenceContract.SideDishEntry.SIDE_DISH_TABLE_NAME, projectionSideDishes, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SideDish sideDish = getSideDishFromCursor(c);

                sideDishes.add(sideDish);
            }

            c.close();
        } else {
            failure = true;
        }

        c = db.query(LocalPersistenceContract.MixtureEntry.MIXTURE_TABLE_NAME, projectionMixture, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Mixture mixture = getMixtureFromCursor(c);

                mixtures.add(mixture);
            }

            c.close();
        } else {
            failure = true;
        }

        c = db.query(LocalPersistenceContract.DishEntry.DISH_TABLE_NAME, projection, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Dish dish = getPartialDishFromCursor(c);

                dishes.add(dish);
            }

            c.close();
        } else {
            failure = true;
        }

        db.close();

        if (failure) {
            callback.onDataNotAvailable();
        } else {
            callback.onDishesLoaded(dishes, sideDishes, mixtures);
        }
    }

    @Override
    public void getDishes(@NonNull LoadDishesCallback callback) {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = mLocalDbHelper.getReadableDatabase();

        String[] projection = {
                LocalPersistenceContract.DishEntry.DISH_COLUMN_ID,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_NAME,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_SIZE,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_PRICE,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_SIDE_DISH_ID
        };

        Cursor c = db.query(
                LocalPersistenceContract.DishEntry.DISH_TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Dish dish = getCompleteDishFromCursor(c);

                dishes.add(dish);

                Log.d(LOG_TAG, "Adding 1 dish to list!!");
            }

            c.close();
        } else {
            // We'll always have dishes, so if cursor = null or count <= 0, then it's a failure.
            callback.onDataNotAvailable();
        }

        db.close();

        callback.onDishesLoaded(dishes);
    }

    @Override
    public void getDrinks(@NonNull LoadDrinksCallback callback) {
        List<Drink> drinks = new ArrayList<>();
        SQLiteDatabase db = mLocalDbHelper.getReadableDatabase();

        String[] projection = {
                LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_ID,
                LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_NAME,
                LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_DESCRIPTION,
                LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_PRICE,
                LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_IMAGE_NAME
        };

        Cursor c = db.query(
                LocalPersistenceContract.DrinkEntry.DRINK_TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Drink drink = getDrinkFromCursor(c);

                drinks.add(drink);

                Log.d(LOG_TAG, "Adding 1 drink to list!!");
            }

            c.close();
        } else {
            // We'll always have dishes, so if cursor = null or count <= 0, then it's a failure.
            callback.onDataNotAvailable();
        }

        db.close();

        callback.onDrinksLoaded(drinks);
    }

    @Override
    public void getDish(@NonNull String dishId, @NonNull GetDishCallback callback) {
        SQLiteDatabase db = mLocalDbHelper.getReadableDatabase();

        String[] projection = {
                LocalPersistenceContract.DishEntry.DISH_COLUMN_ID,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_NAME,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_SIZE,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_PRICE,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID,
                LocalPersistenceContract.DishEntry.DISH_COLUMN_SIDE_DISH_ID
        };

        String selection = LocalPersistenceContract.DishEntry.DISH_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { dishId };

        Cursor c = db.query(
                LocalPersistenceContract.DishEntry.DISH_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Dish dish = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            dish = getCompleteDishFromCursor(c);

            c.close();
        } else {
            // We'll always have dishes, so if cursor = null or count <= 0, then it's a failure.
            callback.onDataNotAvailable();
        }

        db.close();

        if (dish != null) {
            callback.onDishLoaded(dish);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void fillDishes(@NonNull Context context) {
        SQLiteDatabase db = mLocalDbHelper.getWritableDatabase();
        ContentValues values;

        if (isTableEmpty(db, LocalPersistenceContract.DishEntry.DISH_TABLE_NAME)) {
            Log.d(LOG_TAG, "No need to insert again");
            return;
        }

        String currentLine;
        String[] lineValues;
        AssetManager assets = context.getAssets();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("dishes.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_NAME, lineValues[0]);
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION, lineValues[1]);
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_SIZE, lineValues[2]);
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_PRICE, lineValues[3]);
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME, lineValues[4]);
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID, lineValues[5]);
                values.put(LocalPersistenceContract.DishEntry.DISH_COLUMN_SIDE_DISH_ID, lineValues[6]);

                db.insert(LocalPersistenceContract.DishEntry.DISH_TABLE_NAME, null, values);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, STRING_ERROR_WHEN_READING_FILES + e.getMessage(), e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("mixtures.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_NAME, lineValues[0]);
                values.put(LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_DESCRIPTION, lineValues[1]);

                db.insert(LocalPersistenceContract.MixtureEntry.MIXTURE_TABLE_NAME, null, values);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, STRING_ERROR_WHEN_READING_FILES + e.getMessage(), e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("side_dishes.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_NAME, lineValues[0]);
                values.put(LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_DESCRIPTION, lineValues[1]);

                db.insert(LocalPersistenceContract.SideDishEntry.SIDE_DISH_TABLE_NAME, null, values);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, STRING_ERROR_WHEN_READING_FILES + e.getMessage(), e);
        }

        db.close();
    }

    @Override
    public void saveOrder(@NonNull Order order, @NonNull SaveOrderCallback callback) {
        SQLiteDatabase db = mLocalDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_ID, order.getDish().getId());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_NAME, order.getDish().getName());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_DESCRIPTION, order.getDish().getDescription());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_SIZE, order.getDish().getDishSize().toString());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_PRICE, order.getDish().getPrice());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_IMAGE_NAME, order.getDish().getImageName());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_SIDE_DISH_ID, order.getDish().getSideDishesWithCommas());
        values.put(LocalPersistenceContract.HistoryEntry.DISH_COLUMN_MIXTURE_ID, order.getDish().getMixture().getId());

        if (order.getDrink() != null) {
            values.put(LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_ID, order.getDrink().getId());
            values.put(LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_NAME, order.getDrink().getName());
            values.put(LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_DESCRIPTION, order.getDrink().getDescription());
            values.put(LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_PRICE, order.getDrink().getPrice());
            values.put(LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_IMAGE_NAME, order.getDrink().getImageName());
        }

        values.put(LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_PAYMENT_METHOD, order.getPaymentMethod());

        long result = db.insert(LocalPersistenceContract.HistoryEntry.HISTORY_TABLE_NAME, null, values);

        if (result != -1) {
            callback.onSaveOrderSaved(order);
        } else {
            callback.onSaveOrderFailed();
        }

        db.close();
    }

    @Override
    public void getHistory(@NonNull LoadHistoryCallback callBack) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = mLocalDbHelper.getReadableDatabase();

        String[] projection = {
                LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_ID,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_ID,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_NAME,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_DESCRIPTION,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_SIZE,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_PRICE,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_IMAGE_NAME,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_SIDE_DISH_ID,
                LocalPersistenceContract.HistoryEntry.DISH_COLUMN_MIXTURE_ID,
                LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_ID,
                LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_NAME,
                LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_DESCRIPTION,
                LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_PRICE,
                LocalPersistenceContract.HistoryEntry.DRINK_COLUMN_IMAGE_NAME,
                LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_PAYMENT_METHOD
        };

        Cursor c = db.query(
                LocalPersistenceContract.HistoryEntry.HISTORY_TABLE_NAME, projection, null, null, null, null, null);

        if (c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    Order order = getOrderFromCursor(c);

                    orders.add(order);

                    Log.d(LOG_TAG, "Adding 1 order to list!!");
                }

                c.close();
            }
        } else {
            callBack.onDataNotAvailable();
        }

        db.close();

        callBack.onHistoryLoaded(orders);
    }

    @Override
    public void fillDrinks(Context context) {
        SQLiteDatabase db = mLocalDbHelper.getWritableDatabase();
        ContentValues values;

        if (isTableEmpty(db, LocalPersistenceContract.DrinkEntry.DRINK_TABLE_NAME)) {
            Log.d(LOG_TAG, "No need to insert again");
            return;
        }

        String currentLine;
        String[] lineValues;
        AssetManager assets = context.getAssets();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("drinks.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_NAME, lineValues[0]);
                values.put(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_DESCRIPTION, lineValues[1]);
                values.put(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_PRICE, lineValues[2]);
                values.put(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_IMAGE_NAME, lineValues[3]);

                db.insert(LocalPersistenceContract.DrinkEntry.DRINK_TABLE_NAME, null, values);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, STRING_ERROR_WHEN_READING_FILES + e.getMessage(), e);
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

        if (cursor == null) {
            return false;
        }

        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();

        return count > 0;
    }

    /**
     * Gets a order object from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the order object retrieved from the cursor database.
     */
    private Order getOrderFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_ID));

        Dish dish = getCompleteDishFromCursor(c);

        Drink drink = getDrinkFromCursor(c);

        String paymentMethod = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.HistoryEntry.HISTORY_COLUMN_PAYMENT_METHOD));

        return new Order(itemId, dish, drink, paymentMethod);
    }

    /**
     * Gets a complete dish object from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the dish object retrieved from the cursor database.
     */
    private Dish getCompleteDishFromCursor(Cursor c) {
        ArrayList<SideDish> sideDishes = new ArrayList<>();

        Dish defaultDish = getPartialDishFromCursor(c);

        String sideDishesIds = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_SIDE_DISH_ID));
        sideDishes.addAll(getSideDishesFromIds(sideDishesIds));

        return new Dish(defaultDish.getId(), defaultDish.getName(), defaultDish.getDescription(),
                defaultDish.getDishSize(), defaultDish.getPrice(), defaultDish.getImageName(),
                sideDishes, defaultDish.getMixture());
    }

    /**
     * Gets a dish from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the dish object retrieved from the cursor database.
     */
    private Dish getPartialDishFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_ID));
        String name = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_DESCRIPTION));
        DishSize size = DishSize.valueOf(c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_SIZE)));
        float price = c.getFloat(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_PRICE));
        String imageName = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_IMAGE_NAME));

        String mixtureId = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DishEntry.DISH_COLUMN_MIXTURE_ID));
        Mixture mixture = getMixtureFromId(mixtureId);

        return new Dish(itemId, name, description, size, price, imageName, mixture);
    }

    /**
     * Gets a side dish from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the side dish object retrieved from the cursor database.
     */
    private SideDish getSideDishFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_ID));
        String name = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_DESCRIPTION));

        return new SideDish(itemId, name, description);
    }

    /**
     * Gets a mixture from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the mixture object retrieved from the cursor database.
     */
    private Mixture getMixtureFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_ID));
        String name = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_DESCRIPTION));

        return new Mixture(itemId, name, description);
    }

    /**
     * Gets a drink from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the drink object retrieved from the cursor database.
     */
    private Drink getDrinkFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_ID));
        String name = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_DESCRIPTION));
        float price = c.getFloat(c.getColumnIndexOrThrow(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_PRICE));
        String imageName = c.getString(c.getColumnIndexOrThrow(LocalPersistenceContract.DrinkEntry.DRINK_COLUMN_IMAGE_NAME));

        return new Drink(itemId, name, description, price, imageName);
    }

    /**
     * Gets the mixture from a dish.
     *
     * @param id the mixture id.
     * @return the mixture object from a dish.
     */
    @Nullable
    private Mixture getMixtureFromId(String id) {
        Mixture mixture = null;

        try (SQLiteDatabase db = mLocalDbHelper.getReadableDatabase()) {
            String query = "SELECT * FROM " + LocalPersistenceContract.MixtureEntry.MIXTURE_TABLE_NAME
                    + " WHERE " + LocalPersistenceContract.MixtureEntry.MIXTURE_COLUMN_ID + " = '" + id + "'";
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
     * @param sideDishesIds the side dishes ids.
     * @return the list of side dishes objects retrieved from data base.
     */
    private List<SideDish> getSideDishesFromIds(String sideDishesIds) {
        String[] sideDishes = sideDishesIds.split(",");

        SQLiteDatabase db = mLocalDbHelper.getReadableDatabase();
        ArrayList<SideDish> sideDishesList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + LocalPersistenceContract.SideDishEntry.SIDE_DISH_TABLE_NAME
                    + " WHERE " + LocalPersistenceContract.SideDishEntry.SIDE_DISH_COLUMN_ID + " IN (" + makePlaceholders(sideDishes.length) + ")";
            Cursor c = db.rawQuery(query, sideDishes);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    SideDish sideDish = getSideDishFromCursor(c);

                    sideDishesList.add(sideDish);
                }

                c.close();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return sideDishesList;
    }

    /**
     * Inserts placeholders into a string.
     *
     * @param len the number of placeholders.
     * @return a string with all needed placeholders.
     * @throws IOException if something goes wrong with string builder length.
     */
    private String makePlaceholders(int len) throws IOException {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new IOException("No placeholders");
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
