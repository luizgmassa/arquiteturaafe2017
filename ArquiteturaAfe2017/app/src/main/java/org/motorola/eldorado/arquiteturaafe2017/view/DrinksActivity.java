package org.motorola.eldorado.arquiteturaafe2017.view;

import android.os.Bundle;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Drink;
import org.motorola.eldorado.arquiteturaafe2017.presenter.drink.DrinksContract;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.util.List;

public class DrinksActivity extends BaseActivity implements DrinksContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
    }

    @Override
    public void setPresenter(DrinksContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showDrinks(List<Drink> dishes) {

    }
}
