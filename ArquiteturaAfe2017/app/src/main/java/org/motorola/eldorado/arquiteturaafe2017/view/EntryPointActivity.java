package org.motorola.eldorado.arquiteturaafe2017.view;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.data.DataSource;
import org.motorola.eldorado.arquiteturaafe2017.presenter.entrypoint.EntryPointContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.entrypoint.EntryPointPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import static android.accounts.AccountManager.newChooseAccountIntent;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The EntryPoint activity class.
 */
public class EntryPointActivity extends BaseActivity implements EntryPointContract.View {

    /**
     * Holds the Extra integer for Account Selector dialog.
     */
    private static final int EXTRA_ACCOUNT_PICKER = 0;

    /**
     * Holds the Presenter used in this view.
     */
    private EntryPointContract.Presenter mPresenter;

    /**
     * Holds the progress dialog for this view.
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrypoint);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.dishes_activity_loading_dishes));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        mPresenter = new EntryPointPresenter(this, DataSource.getInstance(this), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EXTRA_ACCOUNT_PICKER && resultCode == RESULT_OK) {
            String email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

            mPresenter.saveUser(email);
        } else {
            Toast.makeText(this, R.string.no_account_selected, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Starts the Dishes Activity.
     */
    private void startDishesActivity() {
        Intent intent = new Intent(this, DishesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull EntryPointContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void switchLoadingIndicator() {
        if (mProgressDialog == null || isDestroyed()) {
            return;
        }

        if (mProgressDialog.isShowing()) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void loadedUser(@Nullable String user) {
        if (user == null) {
            Intent intent = newChooseAccountIntent(null, null, new String[]{"com.google"},
                    null, null, null, null);
            startActivityForResult(intent, EXTRA_ACCOUNT_PICKER);
        } else {
            startDishesActivity();
        }
    }

    @Override
    public void savedUser() {
        startDishesActivity();
    }

    @Override
    public void handleError(boolean isLoadError) {
        int strResId = R.string.activity_entry_point_save_user_error;

        if (isLoadError) {
            strResId = R.string.activity_entry_point_load_user_error;
        }

        Toast.makeText(this, strResId, Toast.LENGTH_LONG).show();
        finish();
    }
}
