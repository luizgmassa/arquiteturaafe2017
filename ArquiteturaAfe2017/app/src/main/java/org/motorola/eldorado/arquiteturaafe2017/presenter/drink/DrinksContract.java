package org.motorola.eldorado.arquiteturaafe2017.presenter.drink;

import android.app.Activity;
import android.support.annotation.NonNull;

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
         * Sets the loading indicator to show or hide.
         *
         * @param active true if loading indicator needs to be show on screen, otherwise false.
         */
        void setLoadingIndicator(boolean active);

        /**
         * Sets the received loaded drinks from Presenter to the View (UI).
         *
         * @param dishes the list of loaded drinks.
         */
        void showDrinks(List<Drink> dishes);
    }

    /**
     * The Presenter for Drinks View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Drinks from the Data Source.
         *
         * @param showLoadingUI true if wants to display a loading icon on the View (UI), otherwise false.
         */
        void loadDrinks(boolean showLoadingUI);

        /**
         * Method called when the View (UI) wants to returns the selected drink to previous activity.
         *
         * @param activity the activity.
         * @param selectedDrink the clicked drink item on the View (UI).
         */
        void selectCurrentDrink(Activity activity, @NonNull Drink selectedDrink);
    }
}
