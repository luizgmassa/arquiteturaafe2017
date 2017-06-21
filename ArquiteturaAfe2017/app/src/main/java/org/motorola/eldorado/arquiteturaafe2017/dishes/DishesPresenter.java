package org.motorola.eldorado.arquiteturaafe2017.dishes;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.ArrayList;
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
    public void loadDishes(boolean forceUpdate) {
        loadDishes(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void openDishDetails(@NonNull Dish requestedDish) {
        // TODO open dishdetails activity
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link LocalDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadDishes(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }

        Log.d(LOG_TAG, "Starting loading all dishes...");

        mLocalDataSource.getDishes(new LocalDataSource.LoadDishesCallback() {
            @Override
            public void onDishesLoaded(List<Dish> tasks) {
                if (tasks.isEmpty()) {
                    Log.d(LOG_TAG, "No dishes!!");

                    // Show a message indicating there are no tasks for that filter type.
                    mTasksView.showNoDishes();
                } else {
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
