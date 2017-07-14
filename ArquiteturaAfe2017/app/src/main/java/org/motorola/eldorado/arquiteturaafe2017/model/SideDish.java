package org.motorola.eldorado.arquiteturaafe2017.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

public final class SideDish {

    @NonNull
    private final String mId;

    @NonNull
    private final String mName;

    @Nullable
    private final String mDescription;

    private boolean mIsMixture = false;

    public SideDish(@NonNull String id, @NonNull String name, @Nullable String description,
                    boolean isMixture) {
        mId = id;
        mName = name;
        mDescription = description;
        mIsMixture = isMixture;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getName()) &&
                Strings.isNullOrEmpty(getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SideDish sideDish = (SideDish) o;
        return Objects.equal(getId(), sideDish.getId()) &&
                Objects.equal(getName(), sideDish.getName()) &&
                Objects.equal(getDescription(), sideDish.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Task with title " + getName();
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isMixture() {
        return mIsMixture;
    }
}
