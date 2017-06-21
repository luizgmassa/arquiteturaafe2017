package org.motorola.eldorado.arquiteturaafe2017.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.ArrayList;
import java.util.List;

import org.motorola.eldorado.arquiteturaafe2017.data.DishesPersistenceContract.DishEntry;
import org.motorola.eldorado.arquiteturaafe2017.data.DishesPersistenceContract.SideDishEntry;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class LocalDataSource implements DishesDataSource {

    private static final String LOG_TAG = LocalDataSource.class.getSimpleName();

    private static LocalDataSource mInstance;

    private DbHelper mDbHelper;

    // Static information about dishes

    private static String[] DISHES_NAMES = new String[]{"Feijoada", "Frango assado", "Tilapia"};

    private static String[] DISHES_DESCRIPTIONS = new String[]{"Feijoada muito saborosa brasileira bem temperada", "Frango assado suculento desossado", "Peixe assado sem pele delicioso"};

    private static String[] DISHES_IMAGENAMES = new String[]{"feijoada.png", "frango_assado.png", "tilapia.png"};

    private static String[] DISHES_MIXTURES_IDS = new String[]{"1", "2", "3"};

    private static String[] DISHES_SIDEDISHES_IDS = new String[]{"4,5,6", "7,8,9", "10,11,12"};

    private static String[] SIDEDISHES_NAMES = new String[]{"Feijoada", "Frango assado", "Tilapia", "Nuggets", "Batata frita", "Azeitonas", "Farofa", "Pure de batatas", "Berinjela", "Torta de legumes", "Milho verde", "Molho barbecue"};

    private static String[] SIDEDISHES_DESCRIPTIONS = new String[]{"Feijoada magra", "Frango assado desossado sem pele", "Tilapia assada com temperos e iguarias", "Nuggets de frango assados", "Batata palito frita comum", "Azeitonas em conserva", "Farofa especial temperada com legumes e bacon", "Pure de batatas com temperos especiais", "Berinjela a milanesa", "Torta de legumes assada", "Milho verde em conserva", "Molho barbecue BBQ"};

    private static String[] SIDEDISHES_IS_MIXTURE = new String[]{"1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

    // Prevent direct instantiation.
    private LocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DbHelper(context);

        this.fillDishes();
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
    public void fillDishes() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values;

        if (isTableExists(db, DishEntry.TABLE_NAME)) {
            Log.d(LOG_TAG, "No need to insert again");
            return;
        }

        for (int i = 0; i < 3; i++) {
            values = new ContentValues();
            values.put(DishEntry.COLUMN_NAME_ENTRY_ID, i);
            values.put(DishEntry.COLUMN_NAME_NAME, DISHES_NAMES[i]);
            values.put(DishEntry.COLUMN_NAME_DESCRIPTION, DISHES_DESCRIPTIONS[i]);
            values.put(DishEntry.COLUMN_NAME_IMAGE_NAME, DISHES_IMAGENAMES[i]);
            values.put(DishEntry.COLUMN_NAME_SIDE_DISH_ID, DISHES_SIDEDISHES_IDS[i]);
            values.put(DishEntry.COLUMN_NAME_MIXTURE_ID, DISHES_MIXTURES_IDS[i]);

            db.insert(DishEntry.TABLE_NAME, null, values);
        }

        for (int i = 0; i < 12; i++) {
            values = new ContentValues();
            values.put(SideDishEntry.COLUMN_NAME_ENTRY_ID, i);
            values.put(SideDishEntry.COLUMN_NAME_NAME, SIDEDISHES_NAMES[i]);
            values.put(SideDishEntry.COLUMN_NAME_DESCRIPTION, SIDEDISHES_DESCRIPTIONS[i]);
            values.put(SideDishEntry.COLUMN_NAME_IS_MIXTURE, SIDEDISHES_IS_MIXTURE[i]);

            db.insert(SideDishEntry.TABLE_NAME, null, values);
        }

        db.close();
    }

    boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
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
