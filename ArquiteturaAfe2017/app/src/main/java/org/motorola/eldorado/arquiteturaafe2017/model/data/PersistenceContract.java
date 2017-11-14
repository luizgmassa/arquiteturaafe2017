package org.motorola.eldorado.arquiteturaafe2017.model.data;

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
    interface DishEntry {

        /**
         * Holds the dish id.
         */
        String DISH_COLUMN_ID = "dish_id";

        /**
         * Holds the dish table name.
         */
        String DISH_TABLE_NAME = "dish";

        /**
         * Holds the dish name column name.
         */
        String DISH_COLUMN_NAME = "dish_name";

        /**
         * Holds the dish description column name.
         */
        String DISH_COLUMN_DESCRIPTION = "dish_description";

        /**
         * Holds the dish size column name.
         */
        String DISH_COLUMN_SIZE = "dish_size";

        /**
         * Holds the dish price column name.
         */
        String DISH_COLUMN_PRICE = "dish_price";

        /**
         * Holds the dish image file name column name.
         */
        String DISH_COLUMN_IMAGE_NAME = "dish_image_name";

        /**
         * Holds the side dishes ids column name.
         */
        String DISH_COLUMN_SIDE_DISH_ID = "dish_side_dish_id";

        /**
         * Holds the mixtures ids column name.
         */
        String DISH_COLUMN_MIXTURE_ID = "dish_mixture_id";
    }

    /**
     * Inner class that defines the side dish table contents
     */
    interface SideDishEntry {

        /**
         * Holds the side dish id.
         */
        String SIDE_DISH_COLUMN_ID = "side_dish_id";

        /**
         * Holds the side dish table name.
         */
        String SIDE_DISH_TABLE_NAME = "side_dish";

        /**
         * Holds the side dish name column name.
         */
        String SIDE_DISH_COLUMN_NAME = "side_dish_name";

        /**
         * Holds the side dish description column name.
         */
        String SIDE_DISH_COLUMN_DESCRIPTION = "side_dish_description";
    }

    /**
     * Inner class that defines the mixture table contents
     */
    interface MixtureEntry {

        /**
         * Holds the mixture id.
         */
        String MIXTURE_COLUMN_ID = "mixture_id";

        /**
         * Holds the mixture table name.
         */
        String MIXTURE_TABLE_NAME = "mixture";

        /**
         * Holds the mixture name column name.
         */
        String MIXTURE_COLUMN_NAME = "mixture_name";

        /**
         * Holds the mixture description column name.
         */
        String MIXTURE_COLUMN_DESCRIPTION = "mixture_description";
    }

    /**
     * Inner class that defines the drink table contents
     */
    interface DrinkEntry {

        /**
         * Holds the drink id.
         */
        String DRINK_COLUMN_ID = "drink_id";

        /**
         * Holds the drink table name.
         */
        String DRINK_TABLE_NAME = "drink";

        /**
         * Holds the drink name column name.
         */
        String DRINK_COLUMN_NAME = "drink_name";

        /**
         * Holds the drink description column name.
         */
        String DRINK_COLUMN_DESCRIPTION = "drink_description";

        /**
         * Holds the drink price column name.
         */
        String DRINK_COLUMN_PRICE = "drink_price";

        /**
         * Holds the drink file name column name.
         */
        String DRINK_COLUMN_IMAGE_NAME = "drink_image_name";
    }

    /**
     * Inner class that defines the history table contents
     */
    static abstract class HistoryEntry implements DishEntry, DrinkEntry {

        /**
         * Holds the history id.
         */
        static final String HISTORY_COLUMN_ID = "history_id";

        /**
         * Holds the history table name.
         */
        static final String HISTORY_TABLE_NAME = "history";

        /**
         * Holds the history drink id column name.
         */
        static final String HISTORY_COLUMN_PAYMENT_METHOD = "payment_method";

    }
}
