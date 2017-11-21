package org.motorola.eldorado.arquiteturaafe2017.presenter.entrypoint;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

/**
 * The Entry Point Contract interface.
 */
public interface EntryPointContract {

    /**
     * The View for Entry Point. Connects the Entry Point View to Entry Point Presenter.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Switches the loading indicator to show or hide.
         */
        void switchLoadingIndicator();

        /**
         * Receive the loaded user from Presenter to the View (UI).
         *
         * @param user the user string.
         */
        void loadedUser(@Nullable String user);

        /**
         * Callback used after the user has been saved from Presenter to the View (UI).
         */
        void savedUser();

        /**
         * Callback used to return error from the Presenter to the View (UI).
         *
         * @param isLoadError true if this error is from load user. Otherwise it's a save user error.
         */
        void handleError(boolean isLoadError);
    }

    /**
     * The Presenter for Entry Point View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the load of a user.
         */
        void loadUser();

        /**
         * Saves the received user from View (UI).
         *
         * @param user the user string.
         */
        void saveUser(@NonNull String user);
    }
}
