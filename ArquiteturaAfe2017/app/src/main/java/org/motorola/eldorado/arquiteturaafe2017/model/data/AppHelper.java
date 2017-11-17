package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;

/**
 * Class used for helper methods.
 */
public class AppHelper {

    /**
     * Gets the string containing the side dishes names from the dish object.
     *
     * @param dish the dish object.
     * @return the string of side dishes names.
     */
    public static String getSideDishesNames(@NonNull Dish dish) {
        StringBuilder sideDishesStrBld = new StringBuilder();

        for (SideDish sideDish : dish.getSideDishes()) {
            sideDishesStrBld.append(sideDish.getName()).append(", ");
        }

        return sideDishesStrBld.substring(0, sideDishesStrBld.length() - 2);
    }
}
