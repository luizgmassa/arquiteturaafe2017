package org.motorola.eldorado.arquiteturaafe2017.entrypoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.dishes.DishesActivity;

public class EntrypointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrypoint);

        Intent intent = new Intent(this, DishesActivity.class);
        startActivity(intent);
    }
}
