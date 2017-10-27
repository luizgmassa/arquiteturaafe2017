package org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.view.DishDetailsActivity;
import org.motorola.eldorado.arquiteturaafe2017.view.DrinksActivity;
import org.motorola.eldorado.arquiteturaafe2017.view.EditDishActivity;

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

    @Override
    public void openEditDish(Activity activity, @NonNull Dish dishToEdit) {
        Intent intent = new Intent(activity, EditDishActivity.class);
        intent.putExtra(EditDishActivity.EXTRA_EDIT_DISH, dishToEdit);
        activity.startActivityForResult(intent, DishDetailsActivity.ACTIVITY_RESULT_EDIT_DISH);
    }

    @Override
    public void openPayment(Context context, @NonNull Dish selectedDish) {
        // TODO open payment activity
    }

    @Override
    public void openSelectDrink(Activity activity) {
        Intent intent = new Intent(activity, DrinksActivity.class);
        activity.startActivityForResult(intent, DishDetailsActivity.ACTIVITY_RESULT_DRINK);
    }
}
