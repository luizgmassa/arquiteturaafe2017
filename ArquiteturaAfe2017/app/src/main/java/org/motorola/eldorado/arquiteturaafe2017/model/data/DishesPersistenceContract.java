package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
final class DishesPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DishesPersistenceContract() {
    }

    /* Inner class that defines the table contents */
    static abstract class DishEntry implements BaseColumns {

        static final String TABLE_NAME = "dish";

        static final String COLUMN_NAME_ENTRY_ID = "entry_id";

        static final String COLUMN_NAME_NAME = "name";

        static final String COLUMN_NAME_DESCRIPTION = "description";

        static final String COLUMN_NAME_IMAGE_NAME = "image_name";

        static final String COLUMN_NAME_SIDE_DISH_ID = "side_dish_id";

        static final String COLUMN_NAME_MIXTURE_ID = "mixture_id";
    }

    /* Inner class that defines the table contents */
    static abstract class SideDishEntry implements BaseColumns {

        static final String TABLE_NAME = "side_dish";

        static final String COLUMN_NAME_ENTRY_ID = "entry_id";

        static final String COLUMN_NAME_NAME = "name";

        static final String COLUMN_NAME_DESCRIPTION = "description";

        static final String COLUMN_NAME_IS_MIXTURE = "is_mixture";
    }
}
