package org.motorola.eldorado.arquiteturaafe2017.presenter.drink;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.view.DrinksActivity;

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
     * Holds the access to all local data sources.
     */
    private final LocalDataSource mLocalDataSource;

    /**
     * Holds the instance of View contract.
     */
    private final DrinksContract.View mDrinksView;

    /**
     * Constructor.
     *
     * @param localDataSource the local data source object.
     * @param drinksView the drinks view contract object.
     */
    public DrinksPresenter(@NonNull LocalDataSource localDataSource, @NonNull DrinksContract.View drinksView) {
        mLocalDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
        mDrinksView = checkNotNull(drinksView, "drinksView cannot be null!");

        mDrinksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDrinks(false);
    }

    @Override
    public void loadDrinks(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mDrinksView.setLoadingIndicator(true);
        }

        Log.d(LOG_TAG, "Starting loading all drinks...");

        mLocalDataSource.getDrinks(new LocalDataSource.LoadDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                if (!drinks.isEmpty()) {
                    Log.d(LOG_TAG, "Showing all drinks...");

                    // Show the list of drinks
                    mDrinksView.showDrinks(drinks);
                }

                if (showLoadingUI) {
                    mDrinksView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(LOG_TAG, "No drinks!!");
            }
        });
    }

    @Override
    public void selectCurrentDrink(Activity activity, @NonNull Drink selectedDrink) {
        Intent intent = new Intent();
        intent.putExtra(DrinksActivity.EXTRA_SELECTED_DRINK, selectedDrink);

        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }
}
