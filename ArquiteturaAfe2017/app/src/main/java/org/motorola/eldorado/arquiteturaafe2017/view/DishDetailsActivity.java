package org.motorola.eldorado.arquiteturaafe2017.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail.DishDetailsContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.dishdetail.DishDetailsPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Dish Details activity class.
 */
public class DishDetailsActivity extends BaseActivity implements DishDetailsContract.View {

    /**
     * Holds the Log Tag.
     */
    private static final String LOG_TAG = DishDetailsActivity.class.getSimpleName();

    /**
     * Holds the Activity For Result code for Drink activity.
     */
    public static final int ACTIVITY_RESULT_DRINK = 0;

    /**
     * Holds the Activity For Result code for Edit Dish activity.
     */
    public static final int ACTIVITY_RESULT_EDIT_DISH = 1;

    /**
     * Holds the extra section for intent selected dish.
     */
    public static final String EXTRA_SELECTED_DISH = "selected_dish";

    /**
     * Holds the Presenter used in this view.
     */
    private DishDetailsContract.Presenter mPresenter;

    /**
     * Holds the Image View of the Dish.
     */
    private ImageView mDishImage;

    /**
     * Holds all Text Views for Dish.
     */
    private TextView[] mDishInformationTextViews;

    /**
     * Holds the Text Views IDs.
     */
    private int[] mDishInformationTextViewsIds = {
            R.id.activity_dish_detail_name,
            R.id.activity_dish_detail_description,
            R.id.activity_dish_detail_side_dishes,
            R.id.activity_dish_detail_mixture
    };

    /**
     * Holds the current dish.
     */
    private Dish mCurrentDish;

    /**
     * Holds the selected drink.
     */
    private Drink mSelectedDrink;

    /**
     * Holds the instance of playment button click listener.
     */
    private final ButtonsClickListener mItemListener = new ButtonsClickListener() {

        @Override
        public void onPaymentButtonClick(Dish currentDish, Drink currentDrink) {
            Intent intent = new Intent(DishDetailsActivity.this, PaymentActivity.class);
            intent.putExtra(PaymentActivity.EXTRA_DISH_TO_PAY, currentDish);
            intent.putExtra(PaymentActivity.EXTRA_DRINK_TO_PAY, currentDrink);
            startActivity(intent);
        }

        @Override
        public void onEditDishButtonClick(Dish currentDish) {
            mPresenter.openEditDish(DishDetailsActivity.this, currentDish);
        }

        @Override
        public void onSelectDrinkButtonClick() {
            mPresenter.openSelectDrink(DishDetailsActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dish_detail);

        mPresenter = new DishDetailsPresenter(this);

        // Views
        mDishImage = (ImageView) findViewById(R.id.activity_dish_detail_image);

        Button drinksButton = (Button) findViewById(R.id.activity_dish_detail_drinks_button);
        drinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onSelectDrinkButtonClick();
            }
        });

        Button editDishButton = (Button) findViewById(R.id.activity_dish_detail_edit_dish);
        editDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onEditDishButtonClick(mCurrentDish);
            }
        });

        Button paymentButton = (Button) findViewById(R.id.activity_dish_detail_payment);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onPaymentButtonClick(mCurrentDish, mSelectedDrink);
            }
        });

        mDishInformationTextViews = new TextView[mDishInformationTextViewsIds.length];

        for (int i = 0; i < mDishInformationTextViewsIds.length; i++ ) {
            mDishInformationTextViews[i] = (TextView) findViewById(mDishInformationTextViewsIds[i]);
        }

        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull DishDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showDish() {
        if (getApplicationContext() == null || getBaseContext() == null || getIntent() == null) {
            Log.e(LOG_TAG, "Context is null or intent is null");
            return;
        }

        Bundle data = getIntent().getExtras();
        mCurrentDish = data.getParcelable(EXTRA_SELECTED_DISH);

        if (mCurrentDish == null) {
            Log.e(LOG_TAG, "Received dish is null");
            return;
        }

        updateScreen();
    }

    /**
     * Updates the screen with current dish.
     */
    private void updateScreen() {
        // name
        String name = mCurrentDish.getName();
        mDishInformationTextViews[0].setText(name);

        // description
        String description = mCurrentDish.getDescription();
        mDishInformationTextViews[1].setText(description);

        StringBuilder sideDishesStrBld = new StringBuilder();

        for (SideDish sideDish : mCurrentDish.getSideDishes()) {
            sideDishesStrBld.append(sideDish.getName()).append(", ");
        }

        // side dishes
        String sideDishes = sideDishesStrBld.substring(0, sideDishesStrBld.length() - 2);
        mDishInformationTextViews[2].setText(sideDishes);

        // mixtures
        String mixtures = mCurrentDish.getMixture().getName();
        mDishInformationTextViews[3].setText(mixtures);

        try {
            InputStream ims = getAssets().open(mCurrentDish.getImageName());

            // load image as Drawable
            Drawable drawable = Drawable.createFromStream(ims, null);

            // set image to ImageView
            mDishImage.setImageDrawable(drawable);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error loading dish image: " + e.getMessage(), e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_RESULT_DRINK) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                mSelectedDrink = bundle.getParcelable(DrinksActivity.EXTRA_SELECTED_DRINK);

                if (mSelectedDrink == null) {
                    Log.e(LOG_TAG, "Received drink is null");
                    return;
                }

                TextView drink = (TextView) findViewById(R.id.activity_dish_detail_selected_drink);
                drink.setText(mSelectedDrink.getName());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, R.string.drink_not_selected, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.drink_error, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == ACTIVITY_RESULT_EDIT_DISH) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                mCurrentDish = bundle.getParcelable(EditDishActivity.EXTRA_EDIT_DISH);

                if (mCurrentDish == null) {
                    Log.e(LOG_TAG, "New dish is null");
                    return;
                }

                updateScreen();
            }
        }
    }

    /**
     * The Click Listener interface.
     */
    interface ButtonsClickListener {

        /**
         * The Click Listener method called when user clicks on Payment button.
         *
         * @param currentDish the clicked dish object.
         * @param currentDrink the clicked drink object.
         */
        void onPaymentButtonClick(Dish currentDish, Drink currentDrink);

        /**
         * The Click Listener method called when user clicks on Edit Dish button.
         *
         * @param currentDish the clicked dish object.
         */
        void onEditDishButtonClick(Dish currentDish);

        /**
         * The Click Listener method called when user clicks on Drink button.
         */
        void onSelectDrinkButtonClick();
    }
}
