package org.motorola.eldorado.arquiteturaafe2017.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public final class Dish {

    @NonNull
    private final String mId;

    @NonNull
    private final String mName;

    @Nullable
    private final String mDescription;

    @NonNull
    private final String mImageName;

    @NonNull
    private final List<SideDish> mSideDishes = new ArrayList<>();

    @NonNull
    private final List<SideDish> mMixtures = new ArrayList<>();

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param name       name of the task
     * @param description description of the task
     * @param id          id of the task
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

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getName()) &&
                Strings.isNullOrEmpty(getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;

        return Objects.equal(getId(), dish.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Dish with title " + getName();
    }

    @NonNull
    public List<SideDish> getSideDishes() {
        return mSideDishes;
    }

    @NonNull
    public List<SideDish> getMixtures() {
        return mMixtures;
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

    @NonNull
    public String getImageName() {
        return mImageName;
    }
}
