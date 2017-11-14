package org.motorola.eldorado.arquiteturaafe2017.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * The Order class.
 */
public final class Order implements Parcelable {

    /**
     * Holds the Order id.
     */
    private int mId;

    /**
     * Holds the mixture.
     */
    @NonNull
    private Dish mDish;

    /**
     * Holds the size of the dish.
     */
    @Nullable
    private Drink mDrink;

    /**
     * Holds the Order final price.
     */
    private float mFinalPrice;

    /**
     * Holds the Order payment method.
     */
    @NonNull
    private String mPaymentMethod;

    /**
     * Constructor.
     *
     * @param dish          the ordered dish.
     * @param drink         the ordered drink
     * @param paymentMethod the payment method.
     */
    public Order(@NonNull Dish dish, @Nullable Drink drink, @NonNull String paymentMethod) {
        this(-1, dish, drink, paymentMethod);
    }

    /**
     * Constructor.
     *
     * @param id            the order id.
     * @param dish          the ordered dish.
     * @param drink         the ordered drink
     * @param paymentMethod the payment method.
     */
    public Order(int id, @NonNull Dish dish, @Nullable Drink drink, @NonNull String paymentMethod) {
        mId = id;
        mDrink = drink;
        mDish = dish;
        mPaymentMethod = paymentMethod;
        mFinalPrice = dish.getPrice();

        if (drink != null) {
            mFinalPrice += drink.getPrice();
        }
    }

    protected Order(Parcel in) {
        mId = in.readInt();
        mDish = in.readParcelable(Dish.class.getClassLoader());
        mDrink = in.readParcelable(Drink.class.getClassLoader());
        mFinalPrice = in.readFloat();
        mPaymentMethod = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    /**
     * Empty object method.
     *
     * @return true if this object is empty, otherwise false.
     */
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getDish().toString());
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
        Order order = (Order) o;

        return Objects.equal(getId(), order.getId());
    }

    /**
     * Shows this object's hash code.
     *
     * @return the hash code representing this object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getDish().toString());
    }

    /**
     * Shows this object as a string.
     *
     * @return the string representing this object.
     */
    @Override
    public String toString() {
        return getDish().toString();
    }

    /**
     * Gets the Item id.
     *
     * @return the Item id.
     */
    public int getId() {
        return mId;
    }

    /**
     * Gets the Ordered Dish.
     *
     * @return the ordered dish.
     */
    @NonNull
    public Dish getDish() {
        return mDish;
    }


    /**
     * Gets the Ordered drink.
     *
     * @return the ordered drink.
     */
    @Nullable
    public Drink getDrink() {
        return mDrink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeParcelable(mDish, flags);
        dest.writeParcelable(mDrink, flags);
        dest.writeFloat(mFinalPrice);
        dest.writeString(mPaymentMethod);
    }

    /**
     * Sets the Ordered dish.
     *
     * @param dish the dish ordered.
     */
    public void setMixture(@NonNull Dish dish) {
        this.mDish = dish;
    }

    /**
     * Sets the Ordered drink.
     *
     * @param drink the ordered drink.
     */
    public void setSize(@NonNull Drink drink) {
        this.mDrink = drink;
    }

    /**
     * Gets the Order final price.
     *
     * @return the order final price.
     */
    public float getFinalPrice() {
        return mFinalPrice;
    }

    /**
     * Sets the Order final price.
     *
     * @param finalPrice the order final price.
     */
    public void setFinalPrice(float finalPrice) {
        this.mFinalPrice = finalPrice;
    }

    /**
     * Gets the Order payment method.
     *
     * @return the order payment method.
     */
    public String getPaymentMethod() {
        return mPaymentMethod;
    }

    /**
     * Sets the Order payment method.
     *
     * @param paymentMethod the payment method.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.mPaymentMethod = paymentMethod;
    }
}
