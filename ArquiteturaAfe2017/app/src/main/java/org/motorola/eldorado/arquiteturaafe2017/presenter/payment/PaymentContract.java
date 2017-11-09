package org.motorola.eldorado.arquiteturaafe2017.presenter.payment;

import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

/**
 * The Payment interface.
 */
public interface PaymentContract {

    /**
     * The View for Payment. Connects the Payment View to Payment Presenter.
     */
    interface View extends BaseView<PaymentContract.Presenter> {

        /**
         * Sets the loaded dish from Presenter to the View (UI).
         */
        void showDish();
    }

    /**
     * The Presenter for Payment View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the Payment from the Data Source.
         */
        void loadDish();

        /**
         * Method called when the View (UI) wants to pay for the dish and drink.
         *
         * @param order the order item on the View (UI).
         */
        void doPayment(@NonNull Order order);
    }
}
