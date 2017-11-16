package org.motorola.eldorado.arquiteturaafe2017.view;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    /**
     * Holds the Recycler view instance.
     */
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.history_activity_loading_history));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        // Create the presenter
        mPresenter = new HistoryPresenter(LocalDataSource.getInstance(this), this);

        mListAdapter = new HistoryAdapter(new ArrayList<Order>(0));

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_history_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListAdapter);
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
    public void showHistory(List<Order> history) {
        mListAdapter.replaceData(history);

        TextView emptyView = (TextView) findViewById(android.R.id.empty);

        if (history.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void handleError() {
        Toast.makeText(this, R.string.data_load_error, Toast.LENGTH_LONG).show();
    }

    /**
     * Adapter for the History Activity.
     */
    private static class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

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
        public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
            Order order = mOrders.get(position);

            holder.getMixture().setText(order.getDish().getMixture().toString());

            StringBuilder sideDishesStrBld = new StringBuilder();

            for (SideDish sideDish : order.getDish().getSideDishes()) {
                sideDishesStrBld.append(sideDish.getName()).append(", ");
            }

            String sideDishesNames = sideDishesStrBld.substring(0, sideDishesStrBld.length() - 2);

            holder.getSideDishes().setText(sideDishesNames);

            holder.getDishSize().setText(order.getDish().getDishSize().toString());

            if (order.getDrink() != null) {
                holder.getDrink().setText(order.getDrink().toString());
            }

            holder.getTotalPrice().setText(String.valueOf(order.getFinalPrice()));

            holder.getPaymentMethod().setText(order.getPaymentMethod());

            try {
                InputStream ims = holder.getAssets().open(order.getDish().getImageName());

                // load image as Drawable
                Drawable drawable = Drawable.createFromStream(ims, null);

                // set image to ImageView
                holder.getDishImage().setImageDrawable(drawable);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error loading dish image: " + e.getMessage(), e);
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemCount() {
            return mOrders.size();
        }

        /**
         * Thew View Holder class that provides a reference to the item's view.
         */
        static class ViewHolder extends RecyclerView.ViewHolder {

            /**
             * Holds the Mixture text view.
             */
            private TextView mMixture;

            /**
             * Holds the Side dishes text view.
             */
            private TextView mSideDishes;

            /**
             * Holds the Dish size text view.
             */
            private TextView mDishSize;

            /**
             * Holds the Drink text view.
             */
            private TextView mDrink;

            /**
             * Holds the Total price text view.
             */
            private TextView mTotalPrice;

            /**
             * Holds the Payment method text view.
             */
            private TextView mPaymentMethod;

            /**
             * Holds the Asset Manager to get Assets.
             */
            private AssetManager mAssets;

            /**
             * Holds the Dish image view.
             */
            private ImageView mDishImage;

            /**
             * Constructor.
             *
             * @param view the view.
             */
            ViewHolder(View view) {
                super(view);

                mMixture = (TextView) view.findViewById(R.id.history_item_mixture);
                mSideDishes = (TextView) view.findViewById(R.id.history_item_side_dishes);
                mDishSize = (TextView) view.findViewById(R.id.history_item_dish_size);
                mDrink = (TextView) view.findViewById(R.id.history_item_drink);
                mTotalPrice = (TextView) view.findViewById(R.id.history_item_total_price);
                mPaymentMethod = (TextView) view.findViewById(R.id.history_item_payment_method);
                mDishImage = (ImageView) view.findViewById(R.id.history_item_image);
                mAssets = view.getContext().getAssets();
            }

            /**
             * Gets the Mixture text view.
             *
             * @return the mixture text view.
             */
            public TextView getMixture() {
                return mMixture;
            }

            /**
             * Gets the Side dishes text view.
             *
             * @return the side dishes text view.
             */
            public TextView getSideDishes() {
                return mSideDishes;
            }

            /**
             * Gets the Dish size text view.
             *
             * @return the dish size text view.
             */
            public TextView getDishSize() {
                return mDishSize;
            }

            /**
             * Gets the Drink text view.
             *
             * @return the drink text view.
             */
            public TextView getDrink() {
                return mDrink;
            }

            /**
             * Gets the Total price text view.
             *
             * @return the total price text view.
             */
            public TextView getTotalPrice() {
                return mTotalPrice;
            }

            /**
             * Gets the Payment method text view.
             *
             * @return the payment method text view.
             */
            public TextView getPaymentMethod() {
                return mPaymentMethod;
            }

            /**
             * Gets the Assets manager object.
             *
             * @return the asset manager object.
             */
            AssetManager getAssets() {
                return mAssets;
            }

            /**
             * Gets the Dish image view.
             *
             * @return the dish image view.
             */
            ImageView getDishImage() {
                return mDishImage;
            }
        }
    }
}
