package org.motorola.eldorado.arquiteturaafe2017.presenter.editdish;

import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Edit Dish Presenter class.
 */
public class EditDishPresenter implements EditDishContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = EditDishPresenter.class.getSimpleName();


    /**
     * Holds the access to all local data sources.
     */
    private final DataSource mLocalDataSource;

    /**
     * Holds the instance of View contract.
     */
    private final EditDishContract.View mEditDishView;

    /**
     * Constructor.
     *
     * @param editDishView the edit dish view contract object.
     */
    public EditDishPresenter(@NonNull LocalDataSource localDataSource, @NonNull EditDishContract.View editDishView) {
        mLocalDataSource = checkNotNull(localDataSource, "localDataSource cannot be null");
        mEditDishView = checkNotNull(editDishView, "editDishView cannot be null!");

        mEditDishView.setPresenter(this);
    }

    @Override
    public void start() {
        loadAllDishesInfos();
    }

    @Override
    public void loadAllDishesInfos() {
        mEditDishView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting loading all dishes...");

        mLocalDataSource.getAllInfo(new LocalDataSource.LoadAllInfoCallback() {
            @Override
            public void onDishesLoaded(@NonNull List<Dish> dishes, @NonNull List<SideDish> sideDishes, @NonNull List<Mixture> mixtures) {
                Log.d(LOG_TAG, "Getting all dishes, side dishes and mixtures...");

                // Show the list of dishes
                mEditDishView.showDishes(dishes, sideDishes, mixtures);

                mEditDishView.switchLoadingIndicator();
            }

            @Override
            public void onDataNotAvailable() {
                mEditDishView.switchLoadingIndicator();
                mEditDishView.handleError();

                Log.d(LOG_TAG, "No dishes / side dishes / mixtures !!");
            }
        });
    }
}
