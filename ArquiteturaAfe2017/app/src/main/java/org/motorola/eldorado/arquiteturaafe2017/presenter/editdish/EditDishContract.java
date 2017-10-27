package org.motorola.eldorado.arquiteturaafe2017.presenter.editdish;

import android.app.Activity;
import android.support.annotation.NonNull;

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
         * Sets the loading indicator to show or hide.
         *
         * @param active true if loading indicator needs to be show on screen, otherwise false.
         */
        void setLoadingIndicator(boolean active);

        /**
         * Sets the received dish from Presenter to the View (UI).
         *
         * @param dishes the list of dishes.
         * @param sideDishes the list of side dishes.
         * @param mixtures the list of mixture.
         */
        void showDishes(List<Dish> dishes, List<SideDish> sideDishes, List<Mixture> mixtures);
    }

    /**
     * The Presenter for Edit Dish View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Dishes from the Data Source.
         *
         * @param showLoadingUI true if wants to display a loading icon on the View (UI), otherwise false.
         */
        void loadAllDishesInfos(boolean showLoadingUI);

        /**
         * Method called when the View (UI) wants to save the Dish.
         *
         * @param activity the activity.
         * @param newDish the new edited Dish.
         */
        void saveDish(Activity activity, @NonNull Dish newDish);
    }
}
