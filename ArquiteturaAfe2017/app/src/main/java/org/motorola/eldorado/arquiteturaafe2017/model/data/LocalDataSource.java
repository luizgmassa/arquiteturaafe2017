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
                PersistenceContract.SideDishEntry._ID,
                PersistenceContract.SideDishEntry.COLUMN_NAME_NAME,
                PersistenceContract.SideDishEntry.COLUMN_NAME_DESCRIPTION
        };

        String[] projectionMixture = {
                PersistenceContract.MixtureEntry._ID,
                PersistenceContract.MixtureEntry.COLUMN_NAME_NAME,
                PersistenceContract.MixtureEntry.COLUMN_NAME_DESCRIPTION
        };

        String[] projection = {
                PersistenceContract.DishEntry._ID,
                PersistenceContract.DishEntry.COLUMN_NAME_NAME,
                PersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION,
                PersistenceContract.DishEntry.COLUMN_NAME_SIZE,
                PersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME,
                PersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID
        };

        Cursor c = db.query(PersistenceContract.SideDishEntry.TABLE_NAME, projectionSideDishes, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SideDish sideDish = getSideDishFromCursor(c);

                sideDishes.add(sideDish);
            }

            c.close();
        }

        c = db.query(PersistenceContract.MixtureEntry.TABLE_NAME, projectionMixture, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Mixture mixture = getMixtureFromCursor(c);

                mixtures.add(mixture);
            }

            c.close();
        }

        c = db.query(PersistenceContract.DishEntry.TABLE_NAME, projection, null,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Dish dish = getParcialDishFromCursor(c);

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
                PersistenceContract.DishEntry._ID,
                PersistenceContract.DishEntry.COLUMN_NAME_NAME,
                PersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION,
                PersistenceContract.DishEntry.COLUMN_NAME_SIZE,
                PersistenceContract.DishEntry.COLUMN_NAME_PRICE,
                PersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME,
                PersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID,
                PersistenceContract.DishEntry.COLUMN_NAME_SIDE_DISH_ID
        };

        Cursor c = db.query(
                PersistenceContract.DishEntry.TABLE_NAME, projection, null, null, null, null, null);

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
                PersistenceContract.DrinkEntry._ID,
                PersistenceContract.DrinkEntry.COLUMN_NAME_NAME,
                PersistenceContract.DrinkEntry.COLUMN_NAME_DESCRIPTION,
                PersistenceContract.DrinkEntry.COLUMN_NAME_PRICE,
                PersistenceContract.DrinkEntry.COLUMN_NAME_IMAGE_NAME
        };

        Cursor c = db.query(
                PersistenceContract.DrinkEntry.TABLE_NAME, projection, null, null, null, null, null);

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
                PersistenceContract.DishEntry._ID,
                PersistenceContract.DishEntry.COLUMN_NAME_NAME,
                PersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION,
                PersistenceContract.DishEntry.COLUMN_NAME_SIZE,
                PersistenceContract.DishEntry.COLUMN_NAME_PRICE,
                PersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME,
                PersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID,
                PersistenceContract.DishEntry.COLUMN_NAME_SIDE_DISH_ID
        };

        String selection = PersistenceContract.DishEntry._ID + " LIKE ?";
        String[] selectionArgs = { dishId };

        Cursor c = db.query(
                PersistenceContract.DishEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

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
    public void fillDishes(@NonNull Context context) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values;

        if (!isTableEmpty(db, PersistenceContract.DishEntry.TABLE_NAME)) {
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
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_SIZE, lineValues[2]);
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_PRICE, lineValues[3]);
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME, lineValues[4]);
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID, lineValues[5]);
                values.put(PersistenceContract.DishEntry.COLUMN_NAME_SIDE_DISH_ID, lineValues[6]);

                db.insert(PersistenceContract.DishEntry.TABLE_NAME, null, values);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("mixtures.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(PersistenceContract.MixtureEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(PersistenceContract.MixtureEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);

                db.insert(PersistenceContract.MixtureEntry.TABLE_NAME, null, values);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("side_dishes.info")))) {
            while ((currentLine = br.readLine()) != null) {
                lineValues = currentLine.split(";");

                values = new ContentValues();
                values.put(PersistenceContract.SideDishEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(PersistenceContract.SideDishEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);

                db.insert(PersistenceContract.SideDishEntry.TABLE_NAME, null, values);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when reading files: " + e.getMessage(), e);
        }

        db.close();
    }

    @Override
    public void saveOrder(@NonNull Order order, @NonNull SaveOrderCallback callback) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_DISH_ID, order.getDish().getId());
        values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_DISH_SIZE, order.getDish().getDishSize().getSize());
        values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_MIXTURE_ID, order.getDish().getMixture().getId());
        values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_SIDE_DISHES_IDS, order.getDish().getSideDishesWithCommas());
        values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_DISH_PRICE, order.getDish().getPrice());

        if (order.getDrink() != null) {
            values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_DRINK_ID, order.getDrink().getId());
            values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_DRINK_PRICE, order.getDrink().getPrice());
        }

        values.put(PersistenceContract.HistoryEntry.COLUMN_NAME_PAYMENT_METHOD, order.getPaymentMethod());

        long result = db.insert(PersistenceContract.HistoryEntry.TABLE_NAME, null, values);

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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PersistenceContract.HistoryEntry._ID,
                PersistenceContract.HistoryEntry.COLUMN_NAME_DISH_ID,
                PersistenceContract.HistoryEntry.COLUMN_NAME_DISH_SIZE,
                PersistenceContract.HistoryEntry.COLUMN_NAME_MIXTURE_ID,
                PersistenceContract.HistoryEntry.COLUMN_NAME_SIDE_DISHES_IDS,
                PersistenceContract.HistoryEntry.COLUMN_NAME_DISH_PRICE,
                PersistenceContract.HistoryEntry.COLUMN_NAME_DRINK_ID,
                PersistenceContract.HistoryEntry.COLUMN_NAME_DRINK_PRICE,
                PersistenceContract.HistoryEntry.COLUMN_NAME_PAYMENT_METHOD
        };

        Cursor c = db.query(
                PersistenceContract.HistoryEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Order order = getOrderFromCursor(c);

                orders.add(order);

                Log.d(LOG_TAG, "Adding 1 order to list!!");
            }

            c.close();
        }

        db.close();

        if (orders.isEmpty()) {
            // This will be called if the table is new or just empty.
            callBack.onDataNotAvailable();
        } else {
            callBack.onHistoryLoaded(orders);
        }
    }

    @Override
    public void fillDrinks(Context context) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values;

        if (!isTableEmpty(db, PersistenceContract.DrinkEntry.TABLE_NAME)) {
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
                values.put(PersistenceContract.DrinkEntry.COLUMN_NAME_NAME, lineValues[0]);
                values.put(PersistenceContract.DrinkEntry.COLUMN_NAME_DESCRIPTION, lineValues[1]);
                values.put(PersistenceContract.DrinkEntry.COLUMN_NAME_PRICE, lineValues[2]);
                values.put(PersistenceContract.DrinkEntry.COLUMN_NAME_IMAGE_NAME, lineValues[3]);

                db.insert(PersistenceContract.DrinkEntry.TABLE_NAME, null, values);
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
     * Gets a order object from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the order object retrieved from the cursor database.
     */
    private Order getOrderFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(PersistenceContract.HistoryEntry._ID));
        Dish dish = getCompleteDishFromCursor(c);
        Drink drink = getDrinkFromCursor(c);
        String paymentMethod = c.getString(c.getColumnIndexOrThrow(PersistenceContract.HistoryEntry.COLUMN_NAME_PAYMENT_METHOD));

        return new Order(itemId, dish, drink, paymentMethod);
    }

    /**
     * Gets a complete dish object from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the dish object retrived from the cursor database.
     */
    private Dish getCompleteDishFromCursor(Cursor c) {
        ArrayList<SideDish> sideDishes = new ArrayList<>();

        Dish defaultDish = getParcialDishFromCursor(c);

        String sideDishesIds = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_SIDE_DISH_ID));
        String[] sideDishesIdsSplitted = sideDishesIds.split(",");
        sideDishes.addAll(getSideDishesFromIds(sideDishesIdsSplitted));

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
    private Dish getParcialDishFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(PersistenceContract.DishEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_DESCRIPTION));
        DishSize size = DishSize.valueOf(c.getString(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_SIZE)));
        float price = c.getFloat(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_PRICE));
        String imageName = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_IMAGE_NAME));

        String mixtureId = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DishEntry.COLUMN_NAME_MIXTURE_ID));
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
        int itemId = c.getInt(c.getColumnIndexOrThrow(PersistenceContract.SideDishEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(PersistenceContract.SideDishEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(PersistenceContract.SideDishEntry.COLUMN_NAME_DESCRIPTION));

        return new SideDish(itemId, name, description);
    }

    /**
     * Gets a mixture from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the mixture object retrieved from the cursor database.
     */
    private Mixture getMixtureFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(PersistenceContract.MixtureEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(PersistenceContract.MixtureEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(PersistenceContract.MixtureEntry.COLUMN_NAME_DESCRIPTION));

        return new Mixture(itemId, name, description);
    }

    /**
     * Gets a drink from a single database cursor.
     *
     * @param c the cursor itself.
     * @return the drink object retrieved from the cursor database.
     */
    private Drink getDrinkFromCursor(Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(PersistenceContract.DrinkEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DrinkEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DrinkEntry.COLUMN_NAME_DESCRIPTION));
        float price = c.getFloat(c.getColumnIndexOrThrow(PersistenceContract.DrinkEntry.COLUMN_NAME_PRICE));
        String imageName = c.getString(c.getColumnIndexOrThrow(PersistenceContract.DrinkEntry.COLUMN_NAME_IMAGE_NAME));

        return new Drink(itemId, name, description, price, imageName);
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
            String query = "SELECT * FROM " + PersistenceContract.MixtureEntry.TABLE_NAME
                    + " WHERE " + PersistenceContract.MixtureEntry._ID + " = '" + id + "'";
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
            String query = "SELECT * FROM " + PersistenceContract.SideDishEntry.TABLE_NAME
                    + " WHERE " + PersistenceContract.SideDishEntry._ID + " IN (" + makePlaceholders(sideDishes.length) + ")";
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
