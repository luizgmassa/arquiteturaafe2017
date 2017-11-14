package org.motorola.eldorado.arquiteturaafe2017.presenter.payment;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Payment Presenter class.
 */
public class PaymentPresenter implements PaymentContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = PaymentPresenter.class.getSimpleName();

    /**
     * Holds the instance of View contract.
     */
    private final PaymentContract.View mPaymentView;

    /**
     * Holds the access to all local data sources.
     */
    private final LocalDataSource mLocalDataSource;

    /**
     * Constructor.
     *
     * @param paymentView the payment view contract object.
     */
    public PaymentPresenter(@NonNull LocalDataSource localDataSource, @NonNull PaymentContract.View paymentView) {
        mLocalDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
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
        mPaymentView.setLoadingIndicator(true);

        Log.d(LOG_TAG, "Starting payment...");

        mLocalDataSource.saveOrder(order, new LocalDataSource.SaveOrderCallback() {
            @Override
            public void onSaveOrderSaved(@NonNull Order order) {
                mPaymentView.setLoadingIndicator(false);

                // TODO: send e-mail to restaurant

                // Saves the order and process payment
                // TODO move this to Email part: mPaymentView.onPaymentSuccess();
            }

            @Override
            public void onSaveOrderFailed() {
                mPaymentView.setLoadingIndicator(false);
                mPaymentView.onPaymentError();
                Log.d(LOG_TAG, "Save failed!!");
            }
        });


    }
}
