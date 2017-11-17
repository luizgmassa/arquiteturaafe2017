package org.motorola.eldorado.arquiteturaafe2017.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;
import org.motorola.eldorado.arquiteturaafe2017.model.data.LocalDataSource;
import org.motorola.eldorado.arquiteturaafe2017.presenter.dishes.DishesContract;
import org.motorola.eldorado.arquiteturaafe2017.presenter.dishes.DishesPresenter;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Dishes activity class.
 */
public class DishesActivity extends BaseActivity implements DishesContract.View {

    /**
     * Holds the Log Tag.
     */
    private static final String LOG_TAG = DishesActivity.class.getSimpleName();

    /**
     * Holds the Presenter used in this view.
     */
    private DishesContract.Presenter mPresenter;

    /**
     * Holds the list adapter for this view.
     */
    private DishesAdapter mListAdapter;

    /**
     * Holds the progress dialog for this view.
     */
    private ProgressDialog mProgressDialog;

    /**
     * Holds the Recycler view instance.
     */
    private RecyclerView mRecyclerView;

    /**
     * Holds the instance of dish click item listener.
     */
    private final DishItemListener mItemListener = new DishItemListener() {
        @Override
        public void onDishClick(Dish clickedDish) {
            Intent intent = new Intent(DishesActivity.this, DishDetailsActivity.class);
            intent.putExtra(DishDetailsActivity.EXTRA_SELECTED_DISH, clickedDish);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.dishes_activity_loading_dishes));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        // Create the presenter
        mPresenter = new DishesPresenter(LocalDataSource.getInstance(this), this);

        mListAdapter = new DishesAdapter(new ArrayList<Dish>(0), mItemListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_dishes_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull DishesContract.Presenter presenter) {
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
    public void showDishes(List<Dish> dishes) {
        mListAdapter.replaceData(dishes);

        TextView emptyView = (TextView) findViewById(android.R.id.empty);

        if (dishes.isEmpty()) {
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
     * Adapter for the Dishes Activity.
     */
    private static class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {

        /**
         * The List of dishes.
         */
        private List<Dish> mDishes;

        /**
         * The Dish Click listener.
         */
        private final DishItemListener mItemListener;

        /**
         * Constructor.
         *
         * @param dishes       the list of dishes.
         * @param itemListener the dish click item listener.
         */
        DishesAdapter(List<Dish> dishes, DishItemListener itemListener) {
            setList(dishes);
            mItemListener = itemListener;
        }

        /**
         * Replaces all list items to the received ones and notify the UI.
         *
         * @param dishes the new list of dishes.
         */
        void replaceData(List<Dish> dishes) {
            setList(dishes);
            notifyDataSetChanged();
        }

        /**
         * Sets the list of dishes.
         *
         * @param dishes the list of dishes.
         */
        private void setList(List<Dish> dishes) {
            mDishes = checkNotNull(dishes);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dish_item, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Dish dish = mDishes.get(position);

            holder.getDishName().setText(dish.getName());

            holder.getDishDescription().setText(dish.getDescription());

            try {
                InputStream ims = holder.getAssets().open(dish.getImageName());

                // load image as Drawable
                Drawable drawable = Drawable.createFromStream(ims, null);

                // set image to ImageView
                holder.getDishImage().setImageDrawable(drawable);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error loading dish image: " + e.getMessage(), e);
            }

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onDishClick(dish);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDishes.size();
        }

        /**
         * Thew View Holder class that provides a reference to the item's view.
         */
        static class ViewHolder extends RecyclerView.ViewHolder {

            /**
             * Holds the Dish name text view.
             */
            private TextView mDishName;

            /**
             * Holds the Dish description text view.
             */
            private TextView mDishDescription;

            /**
             * Holds the Asset Manager to get Assets.
             */
            private AssetManager mAssets;

            /**
             * Holds the Dish image view.
             */
            private ImageView mDishImage;

            /**
             * Holds the view.
             */
            private View mView;

            /**
             * Constructor.
             *
             * @param view the view.
             */
            ViewHolder(View view) {
                super(view);

                mDishName = (TextView) view.findViewById(R.id.dish_item_name);
                mDishDescription = (TextView) view.findViewById(R.id.dish_item_description);
                mDishImage = (ImageView) view.findViewById(R.id.dish_item_image);
                mAssets = view.getContext().getAssets();
                mView = view;
            }

            /**
             * Gets the Dish name text view.
             *
             * @return the dish name text view.
             */
            TextView getDishName() {
                return mDishName;
            }

            /**
             * Gets the Dish description text view.
             *
             * @return the dish name text view.
             */
            TextView getDishDescription() {
                return mDishDescription;
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

            /**
             * Sets the click listener for this view.
             *
             * @param listener the click listener.
             */
            void setOnClickListener(View.OnClickListener listener) {
                mView.setOnClickListener(listener);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();

        if (i == R.id.menu_main_history) {
            Intent historyIntent = new Intent(DishesActivity.this, HistoryActivity.class);
            startActivity(historyIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The Dish Item Listener interface.
     */
    interface DishItemListener {

        /**
         * The Click Listener method called when user clicks on a dish.
         *
         * @param clickedDish the clicked dish object.
         */
        void onDishClick(Dish clickedDish);
    }
}
