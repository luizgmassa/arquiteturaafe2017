package org.motorola.eldorado.arquiteturaafe2017.presenter.drink;

import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

import java.util.List;

/**
 * The Drinks Contract interface.
 */
public interface DrinksContract {

    /**
     * The View for Drinks. Connects the Drinks View to Drinks Presenter.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Switches the loading indicator to show or hide.
         */
        void switchLoadingIndicator();

        /**
         * Sets the received loaded drinks from Presenter to the View (UI).
         *
         * @param dishes the list of loaded drinks.
         */
        void showDrinks(List<Drink> dishes);

        /**
         * Callback used to return error from the Presenter to the View (UI).
         */
        void handleError();
    }

    /**
     * The Presenter for Drinks View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Drinks from the Data Source.
         */
        void loadDrinks();
    }
}
