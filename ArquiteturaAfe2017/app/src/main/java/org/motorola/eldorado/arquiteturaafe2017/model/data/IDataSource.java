package org.motorola.eldorado.arquiteturaafe2017.model.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;

import java.util.List;

/**
 * The Local Data Source interface.
 */
public interface IDataSource {

    /**
     * The interface for Load Dishes callback. Do the communication between Dishes Presenter and Data Source.
     */
    interface LoadDishesCallback {

        /**
         * Callback for when dishes have been loaded.
         *
         * @param dishes the list of dishes.
         */
        void onDishesLoaded(@NonNull List<Dish> dishes);

        /**
         * Callback for when dishes data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface for Load History callback. Do the communication between History Presenter and Data Source.
     */
    interface LoadHistoryCallback {

        /**
         * Callback for when history have been loaded.
         *
         * @param history the list of history.
         */
        void onHistoryLoaded(@NonNull List<Order> history);

        /**
         * Callback for when history data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface for Edit Dish callback. Do the communication between Edit Dish Presenter and Data Source.
     */
    interface LoadAllInfoCallback {

        /**
         * Callback for when dishes have been loaded.
         *
         * @param dishes the list of dishes.
         * @param sideDishes the list of side dishes.
         * @param mixtures the list of mixture.
         */
        void onDishesLoaded(@NonNull List<Dish> dishes, @NonNull List<SideDish> sideDishes, @NonNull List<Mixture> mixtures);

        /**
         * Callback for when dishes data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface for Get Dish callback. Do the communication between Dishes Presenter and Data Source.
     */
    interface GetDishCallback {

        /**
         * Callback for when dishes have been loaded.
         *
         * @param dish the dish.
         */
        void onDishLoaded(@NonNull Dish dish);

        /**
         * Callback for when dishes data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface for Load Drinks callback.
     */
    interface LoadDrinksCallback {

        /**
         * Callback for when drinks have been loaded.
         *
         * @param drinks the list of drinks.
         */
        void onDrinksLoaded(@NonNull List<Drink> drinks);

        /**
         * Callback for when drinks data are not available.
         */
        void onDataNotAvailable();
    }

    /**
     * The interface for Save Order callback. Do the communication between History Presenter and Data Source.
     */
    interface SaveOrderCallback {

        /**
         * Callback for when order has been saved.
         *
         * @param order the order.
         */
        void onSaveOrderSaved(@NonNull Order order);

        /**
         * Callback for when order has not been saved
         */
        void onSaveOrderFailed();
    }

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
     * The interface for Save User callback. Do the communication between Entry Point Presenter and Data Source.
     */
    interface SaveUserCallback {

        /**
         * Callback for when user has been saved.
         */
        void onSaveOrderSaved();

        /**
         * Callback for when user has not been saved.
         */
        void onSaveOrderFailed();
    }

    /**
     * The interface for Load User callback. Do the communication between Entry Point Presenter and Data Source.
     */
    interface LoadUserCallback {

        /**
         * Callback for when drinks have been loaded.
         *
         * @param user the user string.
         */
        void onUserLoaded(@Nullable String user);

        /**
         * Callback for when user has not been loaded.
         */
        void onUserLoadFailed();
    }

    /**
     * Saves a user.
     *
     * @param context the context.
     * @param user the user to be saved.
     * @param callback the save user callback.
     */
    void saveUser(@NonNull Context context, @NonNull String user, @NonNull SaveUserCallback callback);

    /**
     * Loads a user.
     *
     * @param context the context.
     * @param callback the load user callback.
     */
    void loadUser(@NonNull Context context, @NonNull LoadUserCallback callback);

    /**
     * Sends a order made by the user.
     *
     * @param context the context.
     * @param order the order to be sent.
     * @param callback the send order callback.
     */
    void sendOrder(@NonNull Context context, @NonNull Order order, @NonNull SendOrderCallback callback);

    /**
     * The callback used to Get All Db Info. Used on Edit Dish Presenter.
     *
     * @param callback the load all info callback.
     */
    void getAllInfo(@NonNull LoadAllInfoCallback callback);

    /**
     * The callback used to Get Dishes. Used on Dishes Presenter.
     *
     * @param callback the load dishes callback.
     */
    void getDishes(@NonNull LoadDishesCallback callback);

    /**
     * The callback used to Get Drinks. Used on Drinks Presenter.
     *
     * @param callback the load drinks callback.
     */
    void getDrinks(@NonNull LoadDrinksCallback callback);

    /**
     * The callback used to Get Dish. Used on Dishes Presenter.
     *
     * @param dishId the requested dish id.
     * @param callback the load dish callback.
     */
    void getDish(@NonNull String dishId, @NonNull GetDishCallback callback);

    /**
     * Fill dishes lists and objects retrieving information from Data Source.
     *
     * @param context the context.
     */
    void fillDishes(@NonNull Context context);

    /**
     * Saves a order made by the user.
     *
     * @param order the order to be saved.
     * @param callback the save order callback.
     */
    void saveOrder(@NonNull Order order, @NonNull SaveOrderCallback callback);

    /**
     * Gets all history of orders made by the user.
     *
     * @param loadHistoryCallback the load history callback.
     */
    void getHistory(@NonNull LoadHistoryCallback loadHistoryCallback);

    /**
     * Fill drinks list and objects retrieving information from Data Source.
     *
     * @param context the context.
     */
    void fillDrinks(@NonNull Context context);
}
