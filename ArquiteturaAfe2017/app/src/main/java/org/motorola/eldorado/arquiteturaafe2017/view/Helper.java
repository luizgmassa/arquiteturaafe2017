package org.motorola.eldorado.arquiteturaafe2017.view;

import android.content.Context;
import android.support.annotation.Nullable;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.DishSize;

import static org.motorola.eldorado.arquiteturaafe2017.model.DishSize.values;

/**
 * The Helper class.
 */
public class Helper {

    /**
     * Gets the Dish size string resource id from Dish size enum.
     *
     * @param dishSize the dish size enum.
     * @return the string resource id of this dish.
     */
    public static int getDishSizeResourceId(DishSize dishSize) {
        int result;

        switch (dishSize) {
            case SMALL:
                result = R.string.dish_size_small;
                break;
            case MEDIUM:
                result = R.string.dish_size_medium;
                break;
            case LARGE:
                result = R.string.dish_size_large;
                break;
            case EXTRA_LARGE:
                result = R.string.dish_size_extra_large;
                break;
            default:
                result = R.string.dish_size_medium;
        }

        return result;
    }

    /**
     * Gets all enums converted to strings from resource.
     *
     * @param context the context.
     * @return the enums converted to strings from resource.
     */
    static String[] getAllDishSizes(Context context) {
        String[] sizes = new String[values().length];

        for (int i = 0; i < values().length; i++ ) {
            sizes[i] = context.getString(Helper.getDishSizeResourceId(values()[i]));
        }

        return sizes;
    }

    /**
     * Gets the Dish enum size by received String
     *
     * @param context the context.
     * @param dishSize the dish size string.
     * @return the dish size enum.
     */
    @Nullable
    static DishSize getDishSizeByString(Context context, String dishSize) {
        String currentSize;
        DishSize result = null;

        for (int i = 0; i < values().length; i++ ) {
            currentSize = context.getString(Helper.getDishSizeResourceId(values()[i]));

            if (currentSize.equals(dishSize)) {
                result = values()[i];
                break;
            }
        }

        return result;
    }
}
