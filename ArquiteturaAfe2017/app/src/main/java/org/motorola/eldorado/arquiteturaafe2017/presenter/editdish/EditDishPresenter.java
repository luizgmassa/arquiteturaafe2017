package org.motorola.eldorado.arquiteturaafe2017.presenter.editdish;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.view.EditDishActivity;

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
    private final LocalDataSource mLocalDataSource;

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
        loadAllDishesInfos(true);
    }

    @Override
    public void loadAllDishesInfos(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mEditDishView.setLoadingIndicator(true);
        }

        Log.d(LOG_TAG, "Starting loading all dishes...");

        mLocalDataSource.getAllInfo(new LocalDataSource.LoadAllInfoCallback() {
            @Override
            public void onDishesLoaded(List<Dish> dishes, List<SideDish> sideDishes, List<Mixture> mixtures) {
                if (!dishes.isEmpty() && !sideDishes.isEmpty() && !mixtures.isEmpty()) {
                    Log.d(LOG_TAG, "Getting all dishes, side dishes and mixtures...");

                    // Show the list of dishes
                    mEditDishView.showDishes(dishes, sideDishes, mixtures);
                }

                if (showLoadingUI) {
                    mEditDishView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(LOG_TAG, "No dishes / side dishes / mixtures !!");
            }
        });
    }

    @Override
    public void saveDish(Activity activity, @NonNull Dish newDish) {
        Intent intent = new Intent();
        intent.putExtra(EditDishActivity.EXTRA_EDIT_DISH, newDish);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }
}
