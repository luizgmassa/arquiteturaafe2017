package org.motorola.eldorado.arquiteturaafe2017.dishes;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by luiz.massa on 08/05/2017.
 */
public class DishesPresenter implements DishesContract.Presenter {

    private static final String LOG_TAG = DishesPresenter.class.getSimpleName();

    private LocalDataSource mLocalDataSource;

    private final DishesContract.View mTasksView;

    private boolean mFirstLoad = true;

    public DishesPresenter(@NonNull LocalDataSource localDataSource, @NonNull DishesContract.View tasksView) {
        mLocalDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");

        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDishes(false);
    }

    @Override
    public void openDishDetails(@NonNull Dish requestedDish) {
        // TODO open dishdetails activity
    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    @Override
    public void loadDishes(final boolean showLoadingUI) {
        mFirstLoad = false;

        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }

        Log.d(LOG_TAG, "Starting loading all dishes...");

        mLocalDataSource.getDishes(new LocalDataSource.LoadDishesCallback() {
            @Override
            public void onDishesLoaded(List<Dish> tasks) {
                if (!tasks.isEmpty()) {
                    Log.d(LOG_TAG, "Showing all dishes...");

                    // Show the list of tasks
                    mTasksView.showDishes(tasks);
                }

                if (showLoadingUI) {
                    mTasksView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(LOG_TAG, "No dishes!!");
            }
        });
    }
}
