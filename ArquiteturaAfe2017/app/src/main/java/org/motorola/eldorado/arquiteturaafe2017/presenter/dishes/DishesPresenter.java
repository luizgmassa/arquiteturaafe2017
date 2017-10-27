package org.motorola.eldorado.arquiteturaafe2017.presenter.dishes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.view.DishDetailsActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Dishes Presenter class.
 */
public class DishesPresenter implements DishesContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = DishesPresenter.class.getSimpleName();

    /**
     * Holds the access to all local data sources.
     */
    private final LocalDataSource mLocalDataSource;

    /**
     * Holds the instance of View contract.
     */
    private final DishesContract.View mDishesView;

    /**
     * Constructor.
     *
     * @param localDataSource the local data source object.
     * @param dishesView the dishes view contract object.
     */
    public DishesPresenter(@NonNull LocalDataSource localDataSource, @NonNull DishesContract.View dishesView) {
        mLocalDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
        mDishesView = checkNotNull(dishesView, "dishesView cannot be null!");

        mDishesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDishes(false);
    }

    @Override
    public void openDishDetails(Context context, @NonNull Dish requestedDish) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DishDetailsActivity.EXTRA_SELECTED_DISH, requestedDish);
        context.startActivity(intent);
    }

    @Override
    public void loadDishes(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mDishesView.setLoadingIndicator(true);
        }

        Log.d(LOG_TAG, "Starting loading all dishes...");

        mLocalDataSource.getDishes(new LocalDataSource.LoadDishesCallback() {
            @Override
            public void onDishesLoaded(List<Dish> dishes) {
                if (!dishes.isEmpty()) {
                    Log.d(LOG_TAG, "Showing all dishes...");

                    // Show the list of dishes
                    mDishesView.showDishes(dishes);
                }

                if (showLoadingUI) {
                    mDishesView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(LOG_TAG, "No dishes!!");
            }
        });
    }
}
