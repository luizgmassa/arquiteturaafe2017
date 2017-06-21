package org.motorola.eldorado.arquiteturaafe2017.dishes;

import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.BaseView;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.List;

public interface DishesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showDishes(List<Dish> dishes);
    }

    interface Presenter extends BasePresenter {

        void loadDishes(boolean forceUpdate);

        void openDishDetails(@NonNull Dish requestedDish);
    }
}
