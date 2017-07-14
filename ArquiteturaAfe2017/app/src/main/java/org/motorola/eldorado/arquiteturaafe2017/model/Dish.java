package org.motorola.eldorado.arquiteturaafe2017.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public final class Dish {

    /**
     * Holds the Dish id.
     */
    @NonNull
    private final String mId;

    /**
     * Holds the Dish name.
     */
    @NonNull
    private final String mName;

    /**
     * Holds the Dish description.
     */
    @Nullable
    private final String mDescription;

    /**
     * Holds the Dish image file name.
     */
    @NonNull
    private final String mImageName;

    /**
     * Holds the list of side dishes.
     */
    @NonNull
    private final List<SideDish> mSideDishes = new ArrayList<>();

    /**
     * Holds the list of mixtures.
     */
    @NonNull
    private final List<SideDish> mMixtures = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param id the id of the dish.
     * @param name the name of the dish.
     * @param description the description of the dish.
     * @param image the image file name of the dish.
     * @param sideDishes the list of side dishes.
     * @param mixtures the list of mixtures.
     */
    public Dish(@NonNull String id, @NonNull String name,
                @Nullable String description, @NonNull String image, List<SideDish> sideDishes, List<SideDish> mixtures) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageName = image;
        mSideDishes.addAll(sideDishes);
        mMixtures.addAll(mixtures);
    }

    /**
     * Empty object method.
     *
     * @return true if this object is empty, otherwise false.
     */
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getName()) &&
                Strings.isNullOrEmpty(getDescription());
    }

    /**
     * Compares one side dish object to this one.
     *
     * @param o the object that wants to be compared.
     * @return true if both objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;

        return Objects.equal(getId(), dish.getId());
    }

    /**
     * Shows this object's hash code.
     *
     * @return the hash code representing this object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getDescription());
    }

    /**
     * Shows this object as a string.
     *
     * @return the string representing this object.
     */
    @Override
    public String toString() {
        return "Dish with title " + getName();
    }

    /**
     * Gets the list of Side Dishes.
     *
     * @return the list of side dishes.
     */
    @NonNull
    public List<SideDish> getSideDishes() {
        return mSideDishes;
    }

    /**
     * Gets the list of Mixtures.
     *
     * @return the list of mixtures.
     */
    @NonNull
    public List<SideDish> getMixtures() {
        return mMixtures;
    }

    /**
     * Gets the Dish id.
     *
     * @return the dish id.
     */
    @NonNull
    public String getId() {
        return mId;
    }

    /**
     * Gets the Dish name.
     *
     * @return the dish name.
     */
    @NonNull
    public String getName() {
        return mName;
    }

    /**
     * Gets the Dish description.
     *
     * @return the dish description.
     */
    @Nullable
    public String getDescription() {
        return mDescription;
    }

    /**
     * Gets the Dish image file name.
     *
     * @return the dish image file name.
     */
    @NonNull
    public String getImageName() {
        return mImageName;
    }
}
