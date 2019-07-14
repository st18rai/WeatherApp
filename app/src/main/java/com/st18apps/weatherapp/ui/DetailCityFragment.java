package com.st18apps.weatherapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.st18apps.weatherapp.R;
import com.st18apps.weatherapp.adapters.ForecastRecyclerAdapter;
import com.st18apps.weatherapp.interfaces.Constants;
import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.network.responses.DetailWeatherResponse;
import com.st18apps.weatherapp.viewmodels.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.st18apps.weatherapp.utils.StringUtil.makeImageUrl;

public class DetailCityFragment extends BaseFragment {
    @BindView(R.id.imageView_weather)
    ImageView imageViewWeather;

    @BindView(R.id.textView_city)
    TextView textViewCity;

    @BindView(R.id.textView_weather)
    TextView textViewWeather;

    @BindView(R.id.textView_current_temperature)
    TextView textViewCurrentTemperature;

    @BindView(R.id.textView_temperature_min_max)
    TextView textViewTemperatureMinMax;

    @BindView(R.id.textView_pressure)
    TextView textViewPressure;

    @BindView(R.id.textView_humidity)
    TextView textViewHumidity;

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;

    private ViewModel viewModel;
    private ForecastRecyclerAdapter adapter;
    private int cityID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_city, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        setRecycler();

        if (getArguments() != null) {
            cityID = getArguments().getInt(Constants.CITY_ID);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((MainActivity) getActivity()).setBackButtonEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();

        ((MainActivity) getActivity()).setBackButtonEnabled(false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getDetailCityWeather(String.valueOf(cityID)).observe(this,
                this::setDataToUI);

    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        recyclerViewForecast.setLayoutManager(linearLayoutManager);

        adapter = new ForecastRecyclerAdapter();
        ScaleInAnimationAdapter newsAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        newsAnimationAdapter.setFirstOnly(false);
        recyclerViewForecast.setAdapter(newsAnimationAdapter);
    }

    private void setDataToUI(DetailWeatherResponse data) {

        WeatherData weatherData = data.getWeatherDataList().get(0);

        textViewCity.setText(data.getCity().getName());
        textViewWeather.setText(weatherData.getWeather().get(0).getDescription());
        textViewCurrentTemperature.setText(String.format("%s °C", weatherData.getMain().getTemp()));
        textViewTemperatureMinMax.setText(String.format("%s°/%s°", weatherData.getMain().getTempMax(),
                weatherData.getMain().getTempMin()));
        textViewPressure.setText(String.format("%s %s %s", getResources().getString(R.string.pressure),
                weatherData.getMain().getPressure(),"мб"));
        textViewHumidity.setText(String.format("%s %s %s", getResources().getString(R.string.humidity),
                weatherData.getMain().getHumidity(),"%"));

        Glide.with(getContext()).load(makeImageUrl(weatherData.getWeather().get(0).getIcon()))
                .into(imageViewWeather);

        adapter.setData(data.getWeatherDataList());

    }
}
