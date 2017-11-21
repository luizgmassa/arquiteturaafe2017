package org.motorola.eldorado.arquiteturaafe2017.presenter.payment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.data.IDataSource;

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
    private final IDataSource mDataSource;

    /**
     * Constructor.
     *
     * @param paymentView the payment view contract object.
     */
    public PaymentPresenter(@NonNull IDataSource dataSource, @NonNull PaymentContract.View paymentView) {
        mDataSource = checkNotNull(dataSource, "dataSource cannot be null");
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
    public void doPayment(@NonNull final Context context, @NonNull Order order) {
        mPaymentView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting payment...");

        mDataSource.saveOrder(order, new IDataSource.SaveOrderCallback() {
            @Override
            public void onSaveOrderSaved(@NonNull Order order) {
                // Saves the order and process payment
                mDataSource.sendOrder(context, order, new IDataSource.SendOrderCallback() {
                    @Override
                    public void onSendOrderSaved(@NonNull Order order) {
                        mPaymentView.switchLoadingIndicator();
                        mPaymentView.paymentSuccess();
                    }

                    @Override
                    public void onSendOrderFailed() {
                        mPaymentView.switchLoadingIndicator();
                        mPaymentView.handleError();

                        Log.d(LOG_TAG, "Send order failed!!");
                    }
                });
            }

            @Override
            public void onSaveOrderFailed() {
                mPaymentView.switchLoadingIndicator();
                mPaymentView.handleError();

                Log.d(LOG_TAG, "Save failed!!");
            }
        });
    }
}
