package org.motorola.eldorado.arquiteturaafe2017.presenter.drink;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.data.IDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Drinks Presenter class.
 */
public class DrinksPresenter implements DrinksContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = DrinksPresenter.class.getSimpleName();

    /**
     * Holds the access to all data sources.
     */
    private final IDataSource mDataSource;

    /**
     * Holds the instance of View contract.
     */
    private final DrinksContract.View mDrinksView;

    /**
     * Constructor.
     *
     * @param dataSource the data source object.
     * @param drinksView the drinks view contract object.
     */
    public DrinksPresenter(@NonNull IDataSource dataSource, @NonNull DrinksContract.View drinksView) {
        mDataSource = checkNotNull(dataSource, "dataSource cannot be null");
        mDrinksView = checkNotNull(drinksView, "drinksView cannot be null!");

        mDrinksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDrinks();
    }

    @Override
    public void loadDrinks() {
        mDrinksView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting loading all drinks...");

        mDataSource.getDrinks(new IDataSource.LoadDrinksCallback() {
            @Override
            public void onDrinksLoaded(@NonNull List<Drink> drinks) {
                Log.d(LOG_TAG, "Showing all drinks...");

                // Show the list of drinks
                mDrinksView.showDrinks(drinks);

                mDrinksView.switchLoadingIndicator();
            }

            @Override
            public void onDataNotAvailable() {
                mDrinksView.switchLoadingIndicator();
                mDrinksView.handleError();


                Log.d(LOG_TAG, "No drinks!!");
            }
        });
    }
}
