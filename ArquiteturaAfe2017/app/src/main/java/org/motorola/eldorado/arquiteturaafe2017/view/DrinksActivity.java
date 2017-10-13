package org.motorola.eldorado.arquiteturaafe2017.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.presenter.drink.DrinksContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.drink.DrinksPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DrinksActivity extends BaseActivity implements DrinksContract.View {

    /**
     * Holds the extra section for selected drink.
     */
    public static final String EXTRA_SELECTED_DRINK = "selected_drink";

    /**
     * Holds the spinner for this view.
     */
    private Spinner mSpinner;

    /**
     * Holds the progress dialog for this view.
     */
    private ProgressDialog mProgressDialog;

    /**
     * Holds the Presenter used in this view.
     */
    private DrinksContract.Presenter mPresenter;

    /**
     * Holds the instance of dish click item listener.
     */
    private final DrinkItemListener mItemListener = new DrinkItemListener() {
        @Override
        public void onSelectButtonClick(Drink selectedDrink) {
            mPresenter.selectCurrentDrink(DrinksActivity.this, selectedDrink);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        setResult(Activity.RESULT_CANCELED);

        mSpinner = (Spinner) findViewById(R.id.activity_drink_spinner);
        Button button = (Button) findViewById(R.id.activity_drink_ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onSelectButtonClick((Drink) mSpinner.getSelectedItem());
            }
        });

        // Create the presenter
        mPresenter = new DrinksPresenter(LocalDataSource.getInstance(this), this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.drink_activity_loading_drinks));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull DrinksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressDialog == null || isDestroyed()) {
            return;
        }

        if (mProgressDialog.isShowing()){
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showDrinks(List<Drink> drinks) {
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, (Drink[]) drinks.toArray());

        mSpinner.setAdapter(spinnerArrayAdapter);
    }

    /**
     * The Drink Item Listener interface.
     */
    interface DrinkItemListener {

        /**
         * The Click Listener method called when user clicks on a drink.
         *
         * @param selectedDrink the selected drink object.
         */
        void onSelectButtonClick(Drink selectedDrink);
    }
}
