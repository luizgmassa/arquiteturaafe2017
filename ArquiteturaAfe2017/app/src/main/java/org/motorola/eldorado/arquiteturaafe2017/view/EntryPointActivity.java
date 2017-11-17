package org.motorola.eldorado.arquiteturaafe2017.view;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.motorola.eldorado.arquiteturaafe2017.R;
import org.motorola.eldorado.arquiteturaafe2017.model.data.UserPreferences;

import static android.accounts.AccountManager.newChooseAccountIntent;

/**
 * The EntryPoint activity class.
 */
public class EntryPointActivity extends AppCompatActivity {

    /**
     * Holds the Extra integer for Account Selector dialog.
     */
    private static final int EXTRA_ACCOUNT_PICKER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrypoint);

        if (UserPreferences.getInstance(this).getUser() == null) {
            Intent intent = newChooseAccountIntent(null, null, new String[]{"com.google"},
                    null, null, null, null);
            startActivityForResult(intent, EXTRA_ACCOUNT_PICKER);
        } else {
            startDishesActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EXTRA_ACCOUNT_PICKER && resultCode == RESULT_OK) {
            String email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

            UserPreferences.getInstance(this).setUser(email);

            startDishesActivity();
        } else {
            Toast.makeText(this, R.string.no_account_selected, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Starts the Dishes Activity.
     */
    private void startDishesActivity() {
        Intent intent = new Intent(this, DishesActivity.class);
        startActivity(intent);
        finish();
    }
}
