package org.motorola.eldorado.arquiteturaafe2017.presenter.payment;

import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Payment Presenter class.
 */
public class PaymentPresenter implements PaymentContract.Presenter {

    /**
     * Holds the instance of View contract.
     */
    private final PaymentContract.View mPaymentView;

    /**
     * Constructor.
     *
     * @param paymentView the payment view contract object.
     */
    public PaymentPresenter(@NonNull PaymentContract.View paymentView) {
        mPaymentView = checkNotNull(paymentView, "paymentView cannot be null!");

        mPaymentView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDish();
    }

    @Override
    public void loadDish() {
        mPaymentView.showDish();
    }

    @Override
    public void doPayment(@NonNull Order order) {
        // TODO: save dish on DB
        // TODO: send e-mail to restaurant
    }
}
