package org.motorola.eldorado.arquiteturaafe2017.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * The Mixture class.
 */
public final class Mixture extends Item implements Parcelable {

    /**
     * Constructor.
     *
     * @param id the id of the Mixture.
     * @param name the name of the Mixture.
     * @param description the description of the Mixture.
     */
    public Mixture(@NonNull String id, @NonNull String name, @Nullable String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    private Mixture(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
    }

    public static final Creator<Mixture> CREATOR = new Creator<Mixture>() {
        @Override
        public Mixture createFromParcel(Parcel in) {
            return new Mixture(in);
        }

        @Override
        public Mixture[] newArray(int size) {
            return new Mixture[size];
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
     * Compares one Mixture object to this one.
     *
     * @param o the object that wants to be compared.
     * @return true if both objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mixture sideDish = (Mixture) o;
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