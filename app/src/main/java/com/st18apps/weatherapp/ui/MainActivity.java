package com.st18apps.weatherapp.ui;

import android.os.Bundle;

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

}
