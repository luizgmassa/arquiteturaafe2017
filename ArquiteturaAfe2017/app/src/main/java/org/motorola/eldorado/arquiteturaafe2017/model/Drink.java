package org.motorola.eldorado.arquiteturaafe2017.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * The Drink class.
 */
public final class Drink extends Item implements Parcelable {

    /**
     * Holds the price of the drink.
     */
    private float mPrice;

    /**
     * Holds the Drink image file name.
     */
    @NonNull
    private final String mImageName;

    /**
     * Constructor.
     *
     * @param id the id of the Drink.
     * @param name the name of the Drink.
     * @param description the description of the Drink.
     * @param price the price of the Drink.
     * @param image the image file name of the Drink.
     */
    public Drink(int id, @NonNull String name,
                 @Nullable String description, float price, @NonNull String image) {
        mId = id;
        mName = name;
        mDescription = description;
        mPrice = price;
        mImageName = image;
    }

    private Drink(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mPrice = in.readFloat();
        mImageName = in.readString();
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
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
     * Compares one side Drink object to this one.
     *
     * @param o the object that wants to be compared.
     * @return true if both objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink Drink = (Drink) o;

        return Objects.equal(getId(), Drink.getId());
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
     * Gets the Drink image file name.
     *
     * @return the Drink image file name.
     */
    @NonNull
    public String getImageName() {
        return mImageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeFloat(mPrice);
        dest.writeString(mImageName);
    }

    /**
     * Gets the Drink price.
     *
     * @return the drink price.
     */
    public float getPrice() {
        return mPrice;
    }
}
