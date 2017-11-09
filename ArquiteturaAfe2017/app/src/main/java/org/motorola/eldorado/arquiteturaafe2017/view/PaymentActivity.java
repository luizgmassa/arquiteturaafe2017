package org.motorola.eldorado.arquiteturaafe2017.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.presenter.payment.PaymentContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.payment.PaymentPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class PaymentActivity extends BaseActivity implements PaymentContract.View {

    /**
     * Holds the Log Tag.
     */
    private static final String LOG_TAG = PaymentActivity.class.getSimpleName();

    public static final String EXTRA_DISH_TO_PAY = "dish_payment";

    public static final String EXTRA_DRINK_TO_PAY = "drink_payment";

    /**
     * Holds the Presenter used in this view.
     */
    private PaymentContract.Presenter mPresenter;

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
    @Nullable
    private Drink mCurrentDrink;

    /**
     * Holds the text view for dish price.
     */
    private TextView mDishPrice;

    /**
     * Holds the text view for drink price.
     */
    private TextView mDrinkPrice;

    /**
     * Holds the text view for final price.
     */
    private TextView mFinalPrice;

    /**
     * Holds the spinner for payment methods.
     */
    private Spinner mPaymentMethod;

    /**
     * Holds the instance of pay button click listener.
     */
    private final PaymentActivity.ButtonsClickListener mItemListener = new PaymentActivity.ButtonsClickListener() {
        @Override
        public void onPayButtonClick(Order order) {
            mPresenter.doPayment(order);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mPresenter = new PaymentPresenter(this);

        // Views
        mDishImage = (ImageView) findViewById(R.id.activity_dish_detail_image);

        mPaymentMethod = (Spinner) findViewById(R.id.activity_payment_payment_methods_spinner);

        mDishPrice = (TextView) findViewById(R.id.activity_payment_dish_price_final);
        mDrinkPrice = (TextView) findViewById(R.id.activity_payment_drink_price_final);
        mFinalPrice = (TextView) findViewById(R.id.activity_payment_final_price_final);

        // we dont need this button here
        ((Button) findViewById(R.id.activity_dish_detail_drinks_button)).setVisibility(Button.GONE);

        Button payButton = (Button) findViewById(R.id.activity_payment_order_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order("0", mCurrentDish, mCurrentDrink,
                        Float.valueOf(mDishPrice.getText().toString()),
                        Float.valueOf(mDrinkPrice.getText().toString()),
                        mPaymentMethod.getSelectedItem().toString());

                mItemListener.onPayButtonClick(order);
            }
        });

        mDishInformationTextViews = new TextView[mDishInformationTextViewsIds.length];

        for (int i = 0; i < mDishInformationTextViewsIds.length; i++) {
            mDishInformationTextViews[i] = (TextView) findViewById(mDishInformationTextViewsIds[i]);
        }

        mPresenter.start();
    }

    @Override
    public void setPresenter(PaymentContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showDish() {
        if (getApplicationContext() == null || getBaseContext() == null || getIntent() == null) {
            Log.e(LOG_TAG, "Context is null or intent is null");
            return;
        }

        Bundle data = getIntent().getExtras();
        mCurrentDish = data.getParcelable(EXTRA_DISH_TO_PAY);
        mCurrentDrink = data.getParcelable(EXTRA_DRINK_TO_PAY);

        if (mCurrentDish == null) {
            Log.e(LOG_TAG, "Dish to pay is null");
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


        mDishPrice.setText(String.valueOf(mCurrentDish.getPrice()));

        float totalPrice = mCurrentDish.getPrice();

        if (mCurrentDrink != null) {
            mDrinkPrice.setText(String.valueOf(mCurrentDrink.getPrice()));

            totalPrice += mCurrentDrink.getPrice();
        }

        mFinalPrice.setText(String.valueOf(totalPrice));
    }

    /**
     * The Click Listener interface.
     */
    interface ButtonsClickListener {

        /**
         * The Click Listener method called when user clicks on Pay button.
         *
         * @param order the order object.
         */
        void onPayButtonClick(Order order);
    }
}
