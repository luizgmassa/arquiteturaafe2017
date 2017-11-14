package org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail;

import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

/**
 * The Dish Details interface.
 */
public interface DishDetailsContract {

    /**
     * The View for Dish Details. Connects the Dishes View to Dishes Presenter.
     */
    interface View extends BaseView<DishDetailsContract.Presenter> {

        /**
         * Sets the loaded dish from Presenter to the View (UI).
         */
        void showDish();
    }

    /**
     * The Presenter for Dishes View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Dishes from the Data Source.
         */
        void loadDish();
    }
}
