package org.motorola.eldorado.arquiteturaafe2017.presenter.dishes;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;

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
    private final DataSource mLocalDataSource;

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
        loadDishes();
    }

    @Override
    public void loadDishes() {
        mDishesView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting loading all dishes...");

        mLocalDataSource.getDishes(new LocalDataSource.LoadDishesCallback() {
            @Override
            public void onDishesLoaded(@NonNull List<Dish> dishes) {
                Log.d(LOG_TAG, "Showing all dishes...");

                // Show the list of dishes
                mDishesView.showDishes(dishes);

                mDishesView.switchLoadingIndicator();
            }

            @Override
            public void onDataNotAvailable() {
                mDishesView.switchLoadingIndicator();
                Log.d(LOG_TAG, "No dishes!!");
            }
        });
    }
}
