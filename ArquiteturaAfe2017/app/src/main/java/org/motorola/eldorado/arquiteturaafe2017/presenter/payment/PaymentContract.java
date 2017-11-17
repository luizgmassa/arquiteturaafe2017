package org.motorola.eldorado.arquiteturaafe2017.presenter.payment;

import android.content.Context;
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
         * Sets the loading indicator to show or hide.
         */
        void switchLoadingIndicator();

        /**
         * Sets the loaded dish from Presenter to the View (UI).
         */
        void showDish();

        /**
         * Callback used to return error from the Presenter to the View (UI).
         */
        void handleError();

        /**
         * Callback used to return success on payment from the Presenter to the View (UI).
         */
        void paymentSuccess();
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
        void doPayment(@NonNull Context context, @NonNull Order order);
    }
}
