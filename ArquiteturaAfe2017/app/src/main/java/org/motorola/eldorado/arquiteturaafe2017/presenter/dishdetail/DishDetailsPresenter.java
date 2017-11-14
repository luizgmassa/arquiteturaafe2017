package org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Dish Details Presenter class.
 */
public class DishDetailsPresenter implements DishDetailsContract.Presenter {

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
}
