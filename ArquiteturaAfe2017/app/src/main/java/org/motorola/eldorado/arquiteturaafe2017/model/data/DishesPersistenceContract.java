package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class DishesPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DishesPersistenceContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class DishEntry implements BaseColumns {

        public static final String TABLE_NAME = "dish";

        public static final String COLUMN_NAME_ENTRY_ID = "entryid";

        public static final String COLUMN_NAME_NAME = "name";

        public static final String COLUMN_NAME_DESCRIPTION = "description";

        public static final String COLUMN_NAME_IMAGE_NAME = "imagename";

        public static final String COLUMN_NAME_SIDE_DISH_ID = "sidedishid";

        public static final String COLUMN_NAME_MIXTURE_ID = "mixtureid";
    }

    /* Inner class that defines the table contents */
    public static abstract class SideDishEntry implements BaseColumns {

        public static final String TABLE_NAME = "sidedish";

        public static final String COLUMN_NAME_ENTRY_ID = "entryid";

        public static final String COLUMN_NAME_NAME = "name";

        public static final String COLUMN_NAME_DESCRIPTION = "description";

        public static final String COLUMN_NAME_IS_MIXTURE = "ismixture";
    }
}
