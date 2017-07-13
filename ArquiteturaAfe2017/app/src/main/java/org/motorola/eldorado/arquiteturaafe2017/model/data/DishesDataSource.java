package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.List;

/**
 * Main entry point for accessing tasks data.
 * <p>
 * For simplicity, only getTasks() and getTask() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new task is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface DishesDataSource {

    interface LoadDishesCallback {

        void onDishesLoaded(List<Dish> dishes);

        void onDataNotAvailable();
    }

    interface GetDishCallback {

        void onDishLoaded(Dish dish);

        void onDataNotAvailable();
    }

    void getDishes(@NonNull LoadDishesCallback callback);

    void getDish(@NonNull String dishId, @NonNull GetDishCallback callback);

    void fillDishes();
}
