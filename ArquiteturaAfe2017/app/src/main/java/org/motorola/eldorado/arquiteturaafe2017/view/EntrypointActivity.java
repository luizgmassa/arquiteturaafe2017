package org.motorola.eldorado.arquiteturaafe2017.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.motorola.eldorado.arquiteturaafe2017.R;

/**
 * The Entrypoint activity class.
 */
public class EntrypointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrypoint);

        Intent intent = new Intent(this, DishesActivity.class);
        startActivity(intent);
    }
}
