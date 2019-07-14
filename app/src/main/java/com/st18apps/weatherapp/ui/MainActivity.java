package com.st18apps.weatherapp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.st18apps.weatherapp.R;
import com.st18apps.weatherapp.utils.FragmentUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUtil.replaceFragment(getSupportFragmentManager(), new CitiesListFragment(),
                false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBackButtonEnabled(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setHomeButtonEnabled(enabled);
        }
    }
}
