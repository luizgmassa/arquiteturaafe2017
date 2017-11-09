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
         * Holds the dish id column name.
         */
        static final String COLUMN_NAME_ENTRY_ID = "entry_id";

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
         * Holds the side dish id column name.
         */
        static final String COLUMN_NAME_ENTRY_ID = "entry_id";

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
         * Holds the mixture id column name.
         */
        static final String COLUMN_NAME_ENTRY_ID = "entry_id";

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
         * Holds the drink id column name.
         */
        static final String COLUMN_NAME_ENTRY_ID = "entry_id";

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
}
