package org.motorola.eldorado.arquiteturaafe2017.presenter.editdish;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

import java.util.List;

/**
 * The Edit Dish Contract interface.
 */
public interface EditDishContract {

    /**
     * The View for Edit Dish. Connects the Edit Dish View to Edit Dish Presenter.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Switches the loading indicator to show or hide.
         */
        void switchLoadingIndicator();

        /**
         * Sets the received dish from Presenter to the View (UI).
         *
         * @param dishes the list of dishes.
         * @param sideDishes the list of side dishes.
         * @param mixtures the list of mixture.
         */
        void showDishes(List<Dish> dishes, List<SideDish> sideDishes, List<Mixture> mixtures);

        /**
         * Callback used to return error from the Presenter to the View (UI).
         */
        void handleError();
    }

    /**
     * The Presenter for Edit Dish View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Dishes from the Data Source.
         */
        void loadAllDishesInfos();
    }
}
