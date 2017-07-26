package org.motorola.eldorado.arquiteturaafe2017.presenter.dishes;

import android.content.Context;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

import java.util.List;

/**
 * The Dishes Contract interface.
 */
public interface DishesContract {

    /**
     * The View for Dishes. Connects the Dishes View to Dishes Presenter.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Sets the loading indicator to show or hide.
         *
         * @param active true if loading indicator needs to be show on screen, otherwise false.
         */
        void setLoadingIndicator(boolean active);

        /**
         * Sets the received loaded dishes from Presenter to the View (UI).
         *
         * @param dishes the list of loaded dishes.
         */
        void showDishes(List<Dish> dishes);
    }

    /**
     * The Presenter for Dishes View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Dishes from the Data Source.
         *
         * @param showLoadingUI true if wants to display a loading icon on the View (UI), otherwise false.
         */
        void loadDishes(boolean showLoadingUI);

        /**
         * Method called when the View (UI) wants to open the Dish Details activity.
         *
         * @param context the context.
         * @param requestedDish the clicked dish item on the View (UI).
         */
        void openDishDetails(Context context, @NonNull Dish requestedDish);
    }
}
