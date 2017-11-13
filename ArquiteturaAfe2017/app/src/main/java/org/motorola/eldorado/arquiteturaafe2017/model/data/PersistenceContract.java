package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.provider.BaseColumns;

/**
 * The Dishes Persistence Contract class.
 */
final class PersistenceContract {

    /**
     * Private constructor.
     */
    private PersistenceContract() {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
    }

    /**
     * Inner class that defines the dish table contents
     */
    static abstract class DishEntry implements BaseColumns {

        /**
         * Holds the dish table name.
         */
        static final String TABLE_NAME = "dish";

        /**
         * Holds the dish name column name.
         */
        static final String COLUMN_NAME_NAME = "name";

        /**
         * Holds the dish description column name.
         */
        static final String COLUMN_NAME_DESCRIPTION = "description";

        /**
         * Holds the dish size column name.
         */
        static final String COLUMN_NAME_SIZE = "size";

        /**
         * Holds the dish price column name.
         */
        static final String COLUMN_NAME_PRICE = "price";

        /**
         * Holds the dish image file name column name.
         */
        static final String COLUMN_NAME_IMAGE_NAME = "image_name";

        /**
         * Holds the side dishes ids column name.
         */
        static final String COLUMN_NAME_SIDE_DISH_ID = "side_dish_id";

        /**
         * Holds the mixtures ids column name.
         */
        static final String COLUMN_NAME_MIXTURE_ID = "mixture_id";
    }

    /**
     * Inner class that defines the side dish table contents
     */
    static abstract class SideDishEntry implements BaseColumns {

        /**
         * Holds the side dish table name.
         */
        static final String TABLE_NAME = "side_dish";

        /**
         * Holds the side dish name column name.
         */
        static final String COLUMN_NAME_NAME = "name";

        /**
         * Holds the side dish description column name.
         */
        static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    /**
     * Inner class that defines the mixture table contents
     */
    static abstract class MixtureEntry implements BaseColumns {

        /**
         * Holds the mixture table name.
         */
        static final String TABLE_NAME = "mixture";

        /**
         * Holds the mixture name column name.
         */
        static final String COLUMN_NAME_NAME = "name";

        /**
         * Holds the mixture description column name.
         */
        static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    /**
     * Inner class that defines the drink table contents
     */
    static abstract class DrinkEntry implements BaseColumns {

        /**
         * Holds the drink table name.
         */
        static final String TABLE_NAME = "drink";

        /**
         * Holds the drink name column name.
         */
        static final String COLUMN_NAME_NAME = "name";

        /**
         * Holds the drink description column name.
         */
        static final String COLUMN_NAME_DESCRIPTION = "description";

        /**
         * Holds the drink price column name.
         */
        static final String COLUMN_NAME_PRICE = "price";

        /**
         * Holds the drink file name column name.
         */
        static final String COLUMN_NAME_IMAGE_NAME = "image_name";
    }

    /**
     * Inner class that defines the history table contents
     */
    static abstract class HistoryEntry implements BaseColumns {

        /**
         * Holds the history table name.
         */
        static final String TABLE_NAME = "history";

        /**
         * Holds the history dish id column name.
         */
        static final String COLUMN_NAME_DISH_ID = "dish_id";

        /**
         * Holds the history dish size id column name.
         */
        static final String COLUMN_NAME_DISH_SIZE = "dish_size";

        /**
         * Holds the history mixture id column name.
         */
        static final String COLUMN_NAME_MIXTURE_ID = "mixture_id";

        /**
         * Holds the history side dishes ids column name.
         */
        static final String COLUMN_NAME_SIDE_DISHES_IDS = "side_dishes_id";

        /**
         * Holds the history drink id column name.
         */
        static final String COLUMN_NAME_DISH_PRICE = "dish_price";

        /**
         * Holds the history drink id column name.
         */
        static final String COLUMN_NAME_DRINK_ID = "drink_id";

        /**
         * Holds the history drink id column name.
         */
        static final String COLUMN_NAME_DRINK_PRICE = "drink_price";

        /**
         * Holds the history drink id column name.
         */
        static final String COLUMN_NAME_PAYMENT_METHOD = "payment_method";

    }
}
