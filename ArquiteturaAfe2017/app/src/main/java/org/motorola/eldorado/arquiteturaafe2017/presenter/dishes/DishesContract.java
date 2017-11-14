package org.motorola.eldorado.arquiteturaafe2017.presenter.dishes;

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
         * Switches the loading indicator to show or hide.
         */
        void switchLoadingIndicator();

        /**
         * Sets the received loaded dishes from Presenter to the View (UI).
         *
         * @param dishes the list of loaded dishes.
         */
        void showDishes(List<Dish> dishes);

        /**
         * Callback used to return error from the Presenter to the View (UI).
         */
        void handleError();
    }

    /**
     * The Presenter for Dishes View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Dishes from the Data Source.
         */
        void loadDishes();
    }
}
