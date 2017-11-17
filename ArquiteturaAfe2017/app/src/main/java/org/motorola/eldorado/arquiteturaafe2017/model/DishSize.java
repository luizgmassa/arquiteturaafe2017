package org.motorola.eldorado.arquiteturaafe2017.model;

import android.content.Context;
import android.support.annotation.Nullable;

import org.motorola.eldorado.arquiteturaafe2017.R;

/**
 * Enum that holds the dish size.
 */
public enum DishSize {
    SMALL(R.string.dish_size_small),
    MEDIUM(R.string.dish_size_medium),
    LARGE(R.string.dish_size_large),
    EXTRA_LARGE(R.string.dish_size_extra_large);

    /**
     * Holds the resource id.
     */
    private final int mResourceId;

    /**
     * Constructor.
     *
     * @param id the resource id.
     */
    DishSize(int id)  {
        mResourceId = id;
    }

    /**
     * Gets the Resource Id for the enum.
     *
     * @return the resource id for the enum.
     */
    public int getResourceId() {
        return mResourceId;
    }

    /**
     * Gets all enums converted to strings from resource.
     *
     * @param context the context.
     * @return the enums converted to strings from resource.
     */
    public static String[] getAllDishSizes(Context context) {
        String[] sizes = new String[DishSize.values().length];

        for (int i = 0; i < DishSize.values().length; i++ ) {
            sizes[i] = context.getString(DishSize.values()[i].getResourceId());
        }

        return sizes;
    }

    @Nullable
    public static DishSize getDishSizeByString(Context context, String dishSize) {
        String currentSize;
        DishSize result = null;

        for (int i = 0; i < DishSize.values().length; i++ ) {
            currentSize = context.getString(DishSize.values()[i].getResourceId());

            if (currentSize.equals(dishSize)) {
                result = DishSize.values()[i];
                break;
            }
        }

        return result;
    }
}
