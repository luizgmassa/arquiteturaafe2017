package org.motorola.eldorado.arquiteturaafe2017.presenter.history;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The History Presenter class.
 */
public class HistoryPresenter implements HistoryContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = HistoryPresenter.class.getSimpleName();

    /**
     * Holds the access to all local data sources.
     */
    private final LocalDataSource mLocalDataSource;

    /**
     * Holds the instance of View contract.
     */
    private final HistoryContract.View mHistoryView;

    /**
     * Constructor.
     *
     * @param localDataSource the local data source object.
     * @param dishesView the History view contract object.
     */
    public HistoryPresenter(@NonNull LocalDataSource localDataSource, @NonNull HistoryContract.View dishesView) {
        mLocalDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
        mHistoryView = checkNotNull(dishesView, "dishesView cannot be null!");

        mHistoryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHistory(false);
    }

    @Override
    public void loadHistory(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mHistoryView.setLoadingIndicator(true);
        }

        Log.d(LOG_TAG, "Starting loading all history...");

        mLocalDataSource.getHistory(new LocalDataSource.LoadHistoryCallback() {
            @Override
            public void onHistoryLoaded(@NonNull List<Order> orders) {
                if (!orders.isEmpty()) {
                    Log.d(LOG_TAG, "Showing all orders...");

                    // Show the list of orders
                    mHistoryView.showHistory(orders);
                }

                if (showLoadingUI) {
                    mHistoryView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(LOG_TAG, "No history!!");
            }
        });
    }
}
