package org.motorola.eldorado.arquiteturaafe2017.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.DishSize;
import org.motorola.eldorado.arquiteturaafe2017.model.Mixture;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.model.Item;
import org.motorola.eldorado.arquiteturaafe2017.presenter.editdish.EditDishContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.editdish.EditDishPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class EditDishActivity extends BaseActivity implements EditDishContract.View {

    /**
     * Holds the Log Tag.
     */
    private static final String LOG_TAG = EditDishActivity.class.getSimpleName();

    public static final String EXTRA_EDIT_DISH = "edit_dish";

    /**
     * Holds the Presenter used in this view.
     */
    private EditDishContract.Presenter mPresenter;

    /**
     * Holds the progress dialog for this view.
     */
    private ProgressDialog mProgressDialog;

    /**
     * Holds the Dish Size spinner.
     */
    private Spinner mDishSizeSpinner;

    /**
     * Holds the Spinner Ids of Mixture and the 3 Side Dishes.
     */
    private int[] mSpinnersIds = {
            R.id.activity_edit_dish_mixture_spinner,
            R.id.activity_edit_dish_side_dish1_spinner,
            R.id.activity_edit_dish_side_dish2_spinner,
            R.id.activity_edit_dish_side_dish3_spinner};

    /**
     * Holds the Text View's descriptions ids.
     */
    private int[] mDescriptionsIds = {
            R.id.activity_edit_dish_mixture_description,
            R.id.activity_edit_dish_side_dish1_description,
            R.id.activity_edit_dish_side_dish2_description,
            R.id.activity_edit_dish_side_dish3_description};

    /**
     * Holds the Spinners instances of Mixture and the 3 Side Dishes.
     */
    private Spinner[] mSpinners;

    /**
     * Holds the Text Views instances of descriptions.
     */
    private TextView[] mDescriptions;

    /**
     * Holds the current dish.
     */
    private Dish mCurrentDish;

    /**
     * Holds the instance of save button click listener.
     */
    private final EditDishActivity.ButtonClickListener mItemListener = new EditDishActivity.ButtonClickListener() {

        @Override
        public void onSaveButtonClick(Dish newDish) {
            mPresenter.saveDish(EditDishActivity.this, newDish);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);

        Bundle data = getIntent().getExtras();
        mCurrentDish = data.getParcelable(EXTRA_EDIT_DISH);

        if (mCurrentDish == null) {
            Log.e(LOG_TAG, "Received dish is null");
            return;
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.edit_dish_activity_loading_dishes));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        mSpinners = new Spinner[mSpinnersIds.length];
        mDescriptions = new TextView[mDescriptionsIds.length];

        for (int i = 0; i < mSpinnersIds.length; i++) {
            mSpinners[i] = (Spinner) findViewById(mSpinnersIds[i]);
            mDescriptions[i] = (TextView) findViewById(mDescriptionsIds[i]);
        }

        mDishSizeSpinner = (Spinner) findViewById(R.id.activity_edit_dish_dish_size_spinner);

        // Create the presenter
        mPresenter = new EditDishPresenter(LocalDataSource.getInstance(this), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(EditDishContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressDialog == null || isDestroyed()) {
            return;
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showDishes(final List<Dish> dishes, List<SideDish> sideDishes, List<Mixture> mixtures) {
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mixtures.toArray());
        ArrayAdapter spinner1ArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sideDishes.toArray());
        ArrayAdapter spinner2ArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sideDishes.toArray());
        ArrayAdapter spinner3ArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,  sideDishes.toArray());
        ArrayAdapter spinner4ArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DishSize.values());

        mSpinners[0].setAdapter(spinnerArrayAdapter);
        mSpinners[1].setAdapter(spinner1ArrayAdapter);
        mSpinners[2].setAdapter(spinner2ArrayAdapter);
        mSpinners[3].setAdapter(spinner3ArrayAdapter);
        mDishSizeSpinner.setAdapter(spinner4ArrayAdapter);

        selectCurrentInformations(spinnerArrayAdapter, spinner1ArrayAdapter,
                spinner2ArrayAdapter, spinner3ArrayAdapter, spinner4ArrayAdapter);

        updateDescriptions();

        Button button = (Button) findViewById(R.id.activity_edit_dish_save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mountNewDish(dishes);

                mItemListener.onSaveButtonClick(mCurrentDish);
            }
        });
    }

    /**
     * Updates all description Text Views with Mixture and Side Dishes descriptions.
     */
    private void updateDescriptions() {
        Object selectedItem;

        for (int i = 0; i < mSpinners.length; i++) {
            final int pos = i;

            // updates all descriptions text views
            selectedItem = mSpinners[pos].getSelectedItem();
            mDescriptions[pos].setText(((Item) selectedItem).getDescription());

            // updates descriptions when user clicks on other items
            mSpinners[pos].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Object selectedItem = mSpinners[pos].getSelectedItem();

                    if (selectedItem instanceof SideDish || selectedItem instanceof Mixture) {
                        mDescriptions[pos].setText(((Item) selectedItem).getDescription());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // nothing.
                }
            });
        }
    }

    /**
     * Selects the current information from received dish.
     *
     * @param mixtureAdapter the mixture adapter.
     * @param sideDish1Adapter the side dish 1 adapter.
     * @param sideDish2Adapter the side dish 2 adapter.
     * @param sideDish3Adapter the side dish 3 adapter.
     * @param dishSizeAdapter the dish size adapter.
     */
    private void selectCurrentInformations(ArrayAdapter mixtureAdapter, ArrayAdapter sideDish1Adapter,
                                           ArrayAdapter sideDish2Adapter, ArrayAdapter sideDish3Adapter,
                                           ArrayAdapter dishSizeAdapter) {
        int spinnerPosition = mixtureAdapter.getPosition(mCurrentDish.getMixture());
        mSpinners[0].setSelection(spinnerPosition);

        spinnerPosition = sideDish1Adapter.getPosition(mCurrentDish.getSideDishes().get(0));
        mSpinners[1].setSelection(spinnerPosition);

        spinnerPosition = sideDish2Adapter.getPosition(mCurrentDish.getSideDishes().get(1));
        mSpinners[2].setSelection(spinnerPosition);

        spinnerPosition = sideDish3Adapter.getPosition(mCurrentDish.getSideDishes().get(2));
        mSpinners[3].setSelection(spinnerPosition);

        spinnerPosition = dishSizeAdapter.getPosition(mCurrentDish.getDishSize());
        mDishSizeSpinner.setSelection(spinnerPosition);
    }

    /**
     * Mounts a new dish based on user selection on this activity.
     *
     * @param dishes the list containing all dishes.
     */
    private void mountNewDish(List<Dish> dishes) {
        Mixture mixture = (Mixture) mSpinners[0].getSelectedItem();

        for (Dish dish : dishes) {
            if (dish.getMixture().getId() == mixture.getId()) {
                mCurrentDish.setName(dish.getName());
                mCurrentDish.setDescription(dish.getDescription());
                mCurrentDish.setImageName(dish.getImageName());
                mCurrentDish.setMixture(mixture);

                ArrayList<SideDish> sideDishes = new ArrayList<>();
                sideDishes.add((SideDish) mSpinners[1].getSelectedItem());
                sideDishes.add((SideDish) mSpinners[2].getSelectedItem());
                sideDishes.add((SideDish) mSpinners[3].getSelectedItem());

                mCurrentDish.setSideDishes(sideDishes);
                mCurrentDish.setDishSize((DishSize) mDishSizeSpinner.getSelectedItem());
                break;
            }
        }
    }

    /**
     * The Click Listener interface.
     */
    interface ButtonClickListener {

        /**
         * The Click Listener method called when user clicks on Save button.
         *
         * @param newDish the new dish object.
         */
        void onSaveButtonClick(Dish newDish);
    }
}
