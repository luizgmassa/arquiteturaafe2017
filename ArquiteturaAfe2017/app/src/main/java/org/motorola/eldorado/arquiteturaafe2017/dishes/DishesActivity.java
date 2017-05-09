package org.motorola.eldorado.arquiteturaafe2017.dishes;

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

import org.motorola.eldorado.arquiteturaafe2017.BaseActivity;
import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Dish;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DishesActivity extends BaseActivity implements DishesContract.View {

    private static final String LOG_TAG = DishesActivity.class.getSimpleName();

    private DishesContract.Presenter mPresenter;

    private DishesAdapter mListAdapter;

    private DishItemListener mItemListener = new DishItemListener() {
        @Override
        public void onDishClick(Dish clickedDish) {
            mPresenter.openDishDetails(clickedDish);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        ArrayList<Dish> dishesList = new ArrayList<>();

        // TODO populate dishes list with dishes from DB

        mListAdapter = new DishesAdapter(dishesList, mItemListener);

        ListView listView = (ListView) findViewById(R.id.activity_dishes_list);
        listView.setAdapter(mListAdapter);
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
    public void showDishes(List<Dish> dishes) {
        mListAdapter.replaceData(dishes);
    }

    @Override
    public void showAddCustomDish() {

    }

    @Override
    public void showNoDishes() {

    }

    private static class DishesAdapter extends BaseAdapter {

        private List<Dish> mDishes;

        private DishItemListener mItemListener;

        public DishesAdapter(List<Dish> tasks, DishItemListener itemListener) {
            setList(tasks);
            mItemListener = itemListener;
        }

        public void replaceData(List<Dish> tasks) {
            setList(tasks);
            notifyDataSetChanged();
        }

        private void setList(List<Dish> tasks) {
            mDishes = checkNotNull(tasks);
        }

        @Override
        public int getCount() {
            return mDishes.size();
        }

        @Override
        public Dish getItem(int i) {
            return mDishes.get(i);
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
                rowView = inflater.inflate(R.layout.dish_item, viewGroup, false);
            }

            final Dish dish = getItem(i);

            TextView name = (TextView) rowView.findViewById(R.id.dish_name);
            name.setText(dish.getName());

            TextView description = (TextView) rowView.findViewById(R.id.dish_description);
            description.setText(dish.getDescription());

            try {
                InputStream ims = rowView.getContext().getAssets().open(dish.getImageName());

                // load image as Drawable
                Drawable drawable = Drawable.createFromStream(ims, null);

                ImageView image = (ImageView) rowView.findViewById(R.id.dish_image);

                // set image to ImageView
                image.setImageDrawable(drawable);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error loading dish image: " + e.getMessage(), e);
            }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onDishClick(dish);
                }
            });

            return rowView;
        }
    }

    public interface DishItemListener {

        void onDishClick(Dish clickedDish);

    }
}
