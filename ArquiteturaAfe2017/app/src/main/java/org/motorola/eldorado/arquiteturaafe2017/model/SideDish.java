package org.motorola.eldorado.arquiteturaafe2017.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * The Side Dish class.
 */
public final class SideDish extends Item implements Parcelable {

    /**
     * Constructor.
     *
     * @param id the id of the side dish.
     * @param name the name of the side dish.
     * @param description the description of the side dish.
     */
    public SideDish(int id, @NonNull String name, @NonNull String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    private SideDish(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
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
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
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
        return getName();
    }
}
