package org.motorola.eldorado.arquiteturaafe2017.view;

import android.os.Bundle;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.Order;
import org.motorola.eldorado.arquiteturaafe2017.presenter.history.HistoryContract;
import org.motorola.eldorado.arquiteturaafe2017.view.base.BaseActivity;

import java.util.List;

public class HistoryActivity extends BaseActivity implements HistoryContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showHistory(List<Order> history) {

    }
}
