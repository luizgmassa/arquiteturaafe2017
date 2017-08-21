package org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail;

import android.content.Context;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
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

        /**
         * Method called when the View (UI) wants to open the Edit Dish activity.
         *
         * @param context the context.
         * @param requestedDish the clicked dish item on the View (UI).
         */
        void openEditDish(Context context, @NonNull Dish requestedDish);

        /**
         * Method called when the View (UI) wants to open the Payment activity.
         *
         * @param context the context.
         * @param requestedDish the clicked dish item on the View (UI).
         */
        void openPayment(Context context, @NonNull Dish requestedDish);

        /**
         * Method called when the View (UI) wants to open the Select Drink activity.
         *
         * @param context the context.
         */
        void openSelectDrink(Context context);
    }
}
