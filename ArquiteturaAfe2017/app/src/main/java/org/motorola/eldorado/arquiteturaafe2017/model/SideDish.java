package org.motorola.eldorado.arquiteturaafe2017.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * The Side Dish class.
 */
public final class SideDish implements Parcelable {

    /**
     * Holds the Side Dish id.
     */
    @NonNull
    private final String mId;

    /**
     * Holds the Side Dish name.
     */
    @NonNull
    private final String mName;

    /**
     * Holds the Side Dish description.
     */
    @Nullable
    private final String mDescription;

    /**
     * Holds if it's a mixture or not.
     */
    private boolean mIsMixture = false;

    /**
     * Constructor.
     *
     * @param id the id of the side dish.
     * @param name the name of the side dish.
     * @param description the description of the side dish.
     * @param isMixture true if this side dish is a mixture, otherwise false.
     */
    public SideDish(@NonNull String id, @NonNull String name, @Nullable String description,
                    boolean isMixture) {
        mId = id;
        mName = name;
        mDescription = description;
        mIsMixture = isMixture;
    }

    protected SideDish(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mIsMixture = in.readByte() != 0;
    }

    public static final Creator<SideDish> CREATOR = new Creator<SideDish>() {
        @Override
        public SideDish createFromParcel(Parcel in) {
            return new SideDish(in);
        }

        @Override
        public SideDish[] newArray(int size) {
            return new SideDish[size];
        }
    };

    @Override
    public int describeContents() {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mIsMixture ? 1 : 0));
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
        SideDish sideDish = (SideDish) o;
        return Objects.equal(getId(), sideDish.getId()) &&
                Objects.equal(getName(), sideDish.getName()) &&
                Objects.equal(getDescription(), sideDish.getDescription());
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
        return "Side Dish with title " + getName();
    }

    /**
     * Gets the Side Dish id.
     *
     * @return the side dish id.
     */
    @NonNull
    public String getId() {
        return mId;
    }

    /**
     * Gets the Side Dish name.
     *
     * @return the side dish name.
     */
    @NonNull
    public String getName() {
        return mName;
    }

    /**
     * Gets the Side Dish description.
     *
     * @return the side dish description.
     */
    @Nullable
    public String getDescription() {
        return mDescription;
    }

    /**
     * Gets the Side Dish mixture.
     *
     * @return true if this side dish is a mixture, otherwise false.
     */
    public boolean isMixture() {
        return mIsMixture;
    }
}
