package com.st18apps.weatherapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.st18apps.weatherapp.R;
import com.st18apps.weatherapp.adapters.CitiesRecyclerAdapter;
import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.viewmodels.ViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class CitiesListFragment extends BaseFragment implements CitiesRecyclerAdapter.ItemClickListener {

    @BindView(R.id.recycler_view_cities)
    RecyclerView recyclerViewCities;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ViewModel viewModel;
    private CitiesRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        setRecycler();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCitiesWeather("709930,703448,702550").observe(this, weatherData ->
                adapter.setData(weatherData));

    }

    private void setRecycler() {
        // check context
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerViewCities.setLayoutManager(linearLayoutManager);

        adapter = new CitiesRecyclerAdapter(this);
        ScaleInAnimationAdapter newsAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        newsAnimationAdapter.setFirstOnly(false);
        recyclerViewCities.setAdapter(newsAnimationAdapter);

        // Handling swipe to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();

                adapter.removeItem(position);

                Snackbar.make(recyclerViewCities, "Город удален!", Snackbar.LENGTH_SHORT)
                        .show();
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewCities);
    }

    private void showCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.city_dialog, null);

        EditText editText = view.findViewById(R.id.editText_city);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setTitle(getResources().getString(R.string.add_city))
                // Add action buttons
                .setPositiveButton(getResources().getString(R.string.ok), (dialog, id) -> {

                    if (!TextUtils.isEmpty(editText.getText().toString()))
                        viewModel.getCityWeather(editText.getText().toString()).observe(this, weatherData -> {
                            {
                                if (weatherData != null && !isCityAlreadyExist(weatherData, adapter.getData()))
                                    adapter.addItem(weatherData);
//                                else
//                                    Toast.makeText(getContext(), "Город уже в списке!", Toast.LENGTH_SHORT).show();
                            }
                        });
                })
                .setNegativeButton(getResources().getString(R.string.cancel), (dialog, id) ->
                        dialog.dismiss());
        builder.create();
        builder.show();

    }

    private boolean isCityAlreadyExist(WeatherData newWeatherData, List<WeatherData> weatherDataList) {

        boolean exist = false;

        for (int i = 0; i < weatherDataList.size(); i++) {
            if (newWeatherData.getId() == weatherDataList.get(i).getId()) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        showCityDialog();
    }

    @Override
    public void onItemClick(int position) {

    }
}
