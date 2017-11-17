package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;

/**
 * The Remote Data Source class.
 */
public class RemoteDataSource implements RemoteData {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = RemoteDataSource.class.getSimpleName();

    /**
     * Holds a instance for this class.
     */
    private static RemoteDataSource mInstance;

    /**
     * Private constructor that prevents direct instantiation.
     */
    private RemoteDataSource() {
        // Nothing.
    }

    /**
     * Get a instance for this Remote Data Source.
     *
     * @return a instance for the Remote Data Source.
     */
    public static RemoteDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new RemoteDataSource();
        }

        return mInstance;
    }

    @Override
    public void sendOrder(@NonNull Context context, @NonNull final Order order, @NonNull final SendOrderCallback callback) {
        String drink = "";
        float drinkPrice = 0;

        if (order.getDrink() != null) {
            drink = "\nBebida: " + order.getDrink().getName();
            drinkPrice = order.getDrink().getPrice();
        }

        String stringBuilder = "Prato: " +
                order.getDish().getName() +
                "\nMistura: " +
                order.getDish().getMixture().getName() +
                "\nAcompanhamentos: " +
                Helper.getSideDishesNames(order.getDish()) +
                "\nTamanho: " +
                context.getString(order.getDish().getDishSize().getResourceId()) +
                drink +
                "\nValor Total: R$" +
                (order.getDish().getPrice() + drinkPrice);

        // sends the email
        BackgroundMail.newBuilder(context)
                .withUsername("quedlcrestaurante@gmail.com")
                .withPassword("quedlc123")
                .withMailto("quedlcrestaurante@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Pedido de " + UserPreferences.getInstance(context).getUser())
                .withBody(stringBuilder)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        callback.onSendOrderSaved(order);
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        callback.onSendOrderFailed();
                    }
                })
                .send();
    }
}
