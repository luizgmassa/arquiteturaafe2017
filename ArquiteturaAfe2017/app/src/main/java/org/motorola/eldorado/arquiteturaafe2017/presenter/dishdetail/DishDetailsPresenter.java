package org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail;

import android.content.Context;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Dish Details Presenter class.
 */
public class DishDetailsPresenter implements DishDetailsContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = DishDetailsPresenter.class.getSimpleName();

    /**
     * Holds the instance of View contract.
     */
    private final DishDetailsContract.View mDishesView;

    /**
     * Constructor.
     *
     * @param dishesView the dishes view contract object.
     */
    public DishDetailsPresenter(@NonNull DishDetailsContract.View dishesView) {
        mDishesView = checkNotNull(dishesView, "dishesView cannot be null!");

        mDishesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDish();
    }

    @Override
    public void loadDish() {
        mDishesView.showDish();
    }

    @Override
    public void openEditDish(Context context, @NonNull Dish requestedDish) {
        // TODO open edit dash activity
    }

    @Override
    public void openPayment(Context context, @NonNull Dish requestedDish) {
        // TODO open payment activity
    }

    @Override
    public void openSelectDrink(Context context) {
        // TODO open select drink activity
    }
}
