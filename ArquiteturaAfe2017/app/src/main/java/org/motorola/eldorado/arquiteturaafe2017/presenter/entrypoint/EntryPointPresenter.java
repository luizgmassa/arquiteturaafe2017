package org.motorola.eldorado.arquiteturaafe2017.presenter.entrypoint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.motorola.eldorado.arquiteturaafe2017.model.data.IDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Entry Point Presenter class.
 */
public class EntryPointPresenter implements EntryPointContract.Presenter {

    /**
     * Holds the Log Tag for this class.
     */
    private static final String LOG_TAG = EntryPointPresenter.class.getSimpleName();

    /**
     * Holds the access to all data sources.
     */
    private final IDataSource mDataSource;

    /**
     * Holds the context instance.
     */
    private final Context mContext;

    /**
     * Holds the instance of View contract.
     */
    private final EntryPointContract.View mEntryPointView;

    /**
     * Constructor.
     *
     * @param context the context.
     * @param dataSource the data source object.
     * @param entryPointView the entry point view contract object.
     */
    public EntryPointPresenter(@NonNull Context context, @NonNull IDataSource dataSource, @NonNull EntryPointContract.View entryPointView) {
        mContext = checkNotNull(context, "context cannot be null");
        mDataSource = checkNotNull(dataSource, "dataSource cannot be null");
        mEntryPointView = checkNotNull(entryPointView, "entryPointView cannot be null!");

        mEntryPointView.setPresenter(this);
    }

    @Override
    public void start() {
        loadUser();
    }

    @Override
    public void loadUser() {
        mEntryPointView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting load of user...");

        mDataSource.loadUser(mContext, new IDataSource.LoadUserCallback() {
            @Override
            public void onUserLoaded(@Nullable String user) {
                Log.d(LOG_TAG, "User loaded!");

                mEntryPointView.loadedUser(user);

                mEntryPointView.switchLoadingIndicator();
            }

            @Override
            public void onUserLoadFailed() {
                mEntryPointView.switchLoadingIndicator();
                mEntryPointView.handleError(true);

                Log.d(LOG_TAG, "Error loading user");
            }
        });
    }

    @Override
    public void saveUser(@NonNull String user) {
        mEntryPointView.switchLoadingIndicator();

        Log.d(LOG_TAG, "Starting save of user...");

        mDataSource.saveUser(mContext, user, new IDataSource.SaveUserCallback() {
            @Override
            public void onSaveOrderSaved() {
                Log.d(LOG_TAG, "User saved!");

                mEntryPointView.savedUser();

                mEntryPointView.switchLoadingIndicator();
            }

            @Override
            public void onSaveOrderFailed() {
                mEntryPointView.switchLoadingIndicator();
                mEntryPointView.handleError(false);

                Log.d(LOG_TAG, "Error saving user");
            }
        });
    }
}
