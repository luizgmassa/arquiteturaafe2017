package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
final class DishesPersistenceContract {

    /**
     * Private constructor.
     */
    private DishesPersistenceContract() {
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

        /**
         * Holds the boolean for mixture or not column name.
         */
        static final String COLUMN_NAME_IS_MIXTURE = "is_mixture";
    }
}
