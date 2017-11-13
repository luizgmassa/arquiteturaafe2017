package org.motorola.eldorado.arquiteturaafe2017.model;

import android.support.annotation.NonNull;

/**
 * Class used for all other models with common variables.
 */
public class Item {

    /**
     * Holds the Item id.
     */
    int mId;

    /**
     * Holds the Item name.
     */
    String mName;

    /**
     * Holds the Item description.
     */
    String mDescription;

    /**
     * Gets the Item id.
     *
     * @return the Item id.
     */
    public int getId() {
        return mId;
    }

    /**
     * Gets the Item name.
     *
     * @return the Item name.
     */
    @NonNull
    public String getName() {
        return mName;
    }

    /**
     * Gets the Item description.
     *
     * @return the Item description.
     */
    @NonNull
    public String getDescription() {
        return mDescription;
    }

    /**
     * Sets the Item Id.
     * 
     * @param id the item id.
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Sets the Item name.
     *
     * @param name the item name.
     */
    public void setName(@NonNull String name) {
        this.mName = name;
    }

    /**
     * Sets the Item description.
     *
     * @param description the item description.
     */
    public void setDescription(@NonNull String description) {
        this.mDescription = description;
    }
}
