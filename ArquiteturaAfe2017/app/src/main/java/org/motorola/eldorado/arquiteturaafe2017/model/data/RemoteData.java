package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.support.annotation.NonNull;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;

/**
 * The Remote Data Source interface.
 */
public interface RemoteData {

    /**
     * The interface for Send Order callback. Do the communication between Payment Presenter and Remote Data Source.
     */
    interface SendOrderCallback {

        /**
         * Callback for when order has been sent.
         *
         * @param order the order.
         */
        void onSendOrderSaved(@NonNull Order order);

        /**
         * Callback for when order has not been sent
         */
        void onSendOrderFailed();
    }

    /**
     * Sends a order made by the user.
     *
     * @param context the context.
     * @param order the order to be sent.
     * @param callback the send order callback.
     */
    void sendOrder(@NonNull Context context, @NonNull Order order, @NonNull SendOrderCallback callback);
}
