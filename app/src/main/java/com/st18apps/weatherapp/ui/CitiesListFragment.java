package com.st18apps.weatherapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.st18apps.weatherapp.R;
import com.st18apps.weatherapp.adapters.CitiesRecyclerAdapter;
import com.st18apps.weatherapp.viewmodels.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
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

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

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
    }

    @Override
    public void onItemClick(int position) {

    }
}
