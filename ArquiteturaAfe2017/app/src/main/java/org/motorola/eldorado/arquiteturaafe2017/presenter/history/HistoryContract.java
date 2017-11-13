package org.motorola.eldorado.arquiteturaafe2017.presenter.history;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.presenter.base.BasePresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseView;

import java.util.List;

/**
 * The History Contract interface.
 */
public interface HistoryContract {

    /**
     * The View for History. Connects the History View to History Presenter.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Sets the loading indicator to show or hide.
         *
         * @param active true if loading indicator needs to be show on screen, otherwise false.
         */
        void setLoadingIndicator(boolean active);

        /**
         * Sets the received loaded History from Presenter to the View (UI).
         *
         * @param history the list of loaded orders.
         */
        void showHistory(List<Order> history);
    }

    /**
     * The Presenter for History View.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method called when the View (UI) requests the History from the Data Source.
         *
         * @param showLoadingUI true if wants to display a loading icon on the View (UI), otherwise false.
         */
        void loadHistory(boolean showLoadingUI);

    }
}
