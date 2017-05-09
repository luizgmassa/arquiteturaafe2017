package org.motorola.eldorado.arquiteturaafe2017.dishes;

import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.BaseView;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.util.List;

public interface DishesContract {

    interface View extends BaseView<Presenter> {

        void showDishes(List<Dish> dishes);

        void showAddCustomDish();

        void showNoDishes();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadDishes(boolean forceUpdate);

        void addNewCustomDish();

        void openDishDetails(@NonNull Dish requestedDish);
    }
}
