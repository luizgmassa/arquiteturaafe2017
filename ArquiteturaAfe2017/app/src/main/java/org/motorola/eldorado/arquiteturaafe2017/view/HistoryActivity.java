package org.motorola.eldorado.arquiteturaafe2017.view;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.model.SideDish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.presenter.history.HistoryContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.history.HistoryPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class HistoryActivity extends BaseActivity implements HistoryContract.View {

    /**
     * Holds the Log Tag.
     */
    private static final String LOG_TAG = HistoryActivity.class.getSimpleName();

    /**
     * Holds the Presenter used in this view.
     */
    private HistoryContract.Presenter mPresenter;

    /**
     * Holds the list adapter for this view.
     */
    private HistoryAdapter mListAdapter;

    /**
     * Holds the progress dialog for this view.
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.dishes_activity_loading_dishes));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        // Create the presenter
        mPresenter = new HistoryPresenter(LocalDataSource.getInstance(this), this);

        mListAdapter = new HistoryAdapter(new ArrayList<Order>(0));

        TextView emptyView = (TextView) findViewById(android.R.id.empty);

        ListView listView = (ListView) findViewById(R.id.activity_history_list);
        listView.setAdapter(mListAdapter);
        listView.setEmptyView(emptyView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull HistoryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
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
    public void showHistory(List<Order> history) {
        mListAdapter.replaceData(history);
    }

    /**
     * Adapter for the History Activity.
     */
    private class HistoryAdapter extends BaseAdapter {

        /**
         * The List of orders.
         */
        private List<Order> mOrders;

        /**
         * Constructor.
         *
         * @param orders the list of orders.
         */
        HistoryAdapter(List<Order> orders) {
            setList(orders);
        }

        /**
         * Replaces all list items to the received ones and notify the UI.
         *
         * @param orders the new list of orders.
         */
        void replaceData(List<Order> orders) {
            setList(orders);
            notifyDataSetChanged();
        }

        /**
         * Sets the list of orders.
         *
         * @param orders the list of orders.
         */
        private void setList(List<Order> orders) {
            mOrders = checkNotNull(orders);
        }

        @Override
        public int getCount() {
            return mOrders.size();
        }

        @Override
        public Order getItem(int i) {
            return mOrders.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;

            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.history_item, viewGroup, false);
            }

            Order order = getItem(i);

            TextView mixture = (TextView) rowView.findViewById(R.id.history_item_mixture);
            mixture.setText(order.getDish().getMixture().toString());

            StringBuilder sideDishesStrBld = new StringBuilder();

            for (SideDish sideDish : order.getDish().getSideDishes()) {
                sideDishesStrBld.append(sideDish.getName()).append(", ");
            }

            String sideDishesNames = sideDishesStrBld.substring(0, sideDishesStrBld.length() - 2);

            TextView sideDishes = (TextView) rowView.findViewById(R.id.history_item_side_dishes);
            sideDishes.setText(sideDishesNames);

            TextView dishSize = (TextView) rowView.findViewById(R.id.history_item_dish_size);
            dishSize.setText(order.getDish().getDishSize().toString());

            TextView drink = (TextView) rowView.findViewById(R.id.history_item_drink);

            if (order.getDrink() != null) {
                drink.setText(order.getDrink().toString());
            }

            TextView totalPrice = (TextView) rowView.findViewById(R.id.history_item_total_price);
            totalPrice.setText(String.valueOf(order.getFinalPrice()));

            TextView paymentMethod = (TextView) rowView.findViewById(R.id.history_item_payment_method);
            paymentMethod.setText(order.getPaymentMethod());

            ImageView dishImage = (ImageView) rowView.findViewById(R.id.history_item_image);

            try {
                InputStream ims = getAssets().open(order.getDish().getImageName());

                // load image as Drawable
                Drawable drawable = Drawable.createFromStream(ims, null);

                // set image to ImageView
                dishImage.setImageDrawable(drawable);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error loading dish image: " + e.getMessage(), e);
            }

            return rowView;
        }
    }
}
