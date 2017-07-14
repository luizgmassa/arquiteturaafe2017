package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.List;

interface DishesDataSource {

    /**
     * The interface for Load Dishes callback. Do the communication between Dishes Presenter and Data Source.
     */
    interface LoadDishesCallback {

        /**
         * Callback for when dishes have been loaded.
         *
         * @param dishes the list of dishes.
         */
        void onDishesLoaded(List<Dish> dishes);

        /**
         * Callback for when dishes data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface for Get Dish callback. Do the communication between Dishes Presenter and Data Source.
     */
    interface GetDishCallback {

        /**
         * Callback for when dishes have been loaded.
         *
         * @param dish the dish.
         */
        void onDishLoaded(Dish dish);

        /**
         * Callback for when dishes data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The callback used to Get Dishes. Used on Dishes Presenter.
     *
     * @param callback the load dishes callback.
     */
    void getDishes(@NonNull LoadDishesCallback callback);

    /**
     * The callback used to Get Dish. Used on Dishes Presenter.
     *
     * @param dishId the requested dish id.
     * @param callback the load dish callback.
     */
    void getDish(@NonNull String dishId, @NonNull GetDishCallback callback);

    /**
     * Fill dishes lists and objects retrieving information from Data Source.
     *
     * @param context the context.
     */
    void fillDishes(Context context);
}
