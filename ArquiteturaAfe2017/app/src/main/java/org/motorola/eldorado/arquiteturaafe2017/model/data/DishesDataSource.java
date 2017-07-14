package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.List;

interface DishesDataSource {

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

    void fillDishes(Context context);
}
