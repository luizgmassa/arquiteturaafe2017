package org.motorola.eldorado.arquiteturaafe2017.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * The Dish class.
 */
public final class Dish extends Item implements Parcelable {

    /**
     * Holds the Dish image file name.
     */
    @NonNull
    private String mImageName;

    /**
     * Holds the list of side dishes.
     */
    private List<SideDish> mSideDishes = new ArrayList<>();

    /**
     * Holds the mixture.
     */
    @NonNull
    private Mixture mMixture;

    /**
     * Holds the size of the dish.
     */
    @NonNull
    private DishSize mSize;

    /**
     * Constructor.
     *
     * @param id the id of the dish.
     * @param name the name of the dish.
     * @param description the description of the dish.
     * @param image the image file name of the dish.
     * @param sideDishes the list of side dishes.
     * @param mixture the mixture.
     */
    public Dish(@NonNull String id, @NonNull String name, @NonNull String description,
                @NonNull DishSize size, @NonNull String image, @NonNull List<SideDish> sideDishes,
                @NonNull Mixture mixture) {
        this(id, name, description, size, image, mixture);
        mSideDishes.addAll(sideDishes);
    }

    /**
     * Constructor.
     *
     * @param id the id of the dish.
     * @param name the name of the dish.
     * @param description the description of the dish.
     * @param image the image file name of the dish.
     */
    public Dish(@NonNull String id, @NonNull String name, @NonNull String description,
                @NonNull DishSize size, @NonNull String image, @NonNull Mixture mixture) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageName = image;
        mSize = size;
        mMixture = mixture;
    }

    protected Dish(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mImageName = in.readString();
        mSize = DishSize.valueOf(in.readString());
        mSideDishes = in.createTypedArrayList(SideDish.CREATOR);
        mMixture = in.readParcelable(SideDish.class.getClassLoader());
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

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
        return getName();
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
     * Gets the Mixture.
     *
     * @return the mixture.
     */
    @NonNull
    public Mixture getMixture() {
        return mMixture;
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

    /**
     * Gets the Dish size.
     *
     * @return the dish size.
     */
    @NonNull
    public DishSize getSize() {
        return mSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mImageName);
        dest.writeString(mSize.name());
        dest.writeTypedList(mSideDishes);
        dest.writeParcelable(mMixture, flags);
    }

    /**
     * Sets the Dish image name.
     *
     * @param imageName the dish image name.
     */
    public void setImageName(@NonNull String imageName) {
        this.mImageName = imageName;
    }

    /**
     * Sets the dish Side Dishes list.
     *
     * @param sideDishes the dish side dishes list.
     */
    public void setSideDishes(@NonNull List<SideDish> sideDishes) {
        this.mSideDishes = sideDishes;
    }

    /**
     * Sets the Dish mixture
     *
     * @param mixture the dish mixture.
     */
    public void setMixture(@NonNull Mixture mixture) {
        this.mMixture = mixture;
    }

    /**
     * Sets the Dish size.
     *
     * @param size the dish size.
     */
    public void setSize(@NonNull DishSize size) {
        this.mSize = size;
    }
}
