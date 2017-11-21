package org.motorola.eldorado.arquiteturaafe2017.presenter.history;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.data.IDataSource;

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
    private final IDataSource mDataSource;

    /**
     * Holds the instance of View contract.
     */
    private final HistoryContract.View mHistoryView;

    /**
     * Constructor.
     *
     * @param localDataSource the local data source object.
     * @param historyView the History view contract object.
     */
    public HistoryPresenter(@NonNull DataSource localDataSource, @NonNull HistoryContract.View historyView) {
        mDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
        mHistoryView = checkNotNull(historyView, "historyView cannot be null!");

        mHistoryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHistory();
    }

    @Override
    public void loadHistory() {
        mHistoryView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting loading all history...");

        mDataSource.getHistory(new DataSource.LoadHistoryCallback() {
            @Override
            public void onHistoryLoaded(@NonNull List<Order> orders) {
                Log.d(LOG_TAG, "Showing all orders...");

                // Show the list of orders
                mHistoryView.showHistory(orders);

                mHistoryView.switchLoadingIndicator();
            }

            @Override
            public void onDataNotAvailable() {
                mHistoryView.switchLoadingIndicator();
                mHistoryView.handleError();

                Log.d(LOG_TAG, "No history!!");
            }
        });
    }
}
