package com.st18apps.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.st18apps.weatherapp.R;
import com.st18apps.weatherapp.model.WeatherData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder> {
    private List<WeatherData> data;

    public ForecastRecyclerAdapter() {
    }

    public List<WeatherData> getData() {
        return data;
    }

    public void setData(List<WeatherData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);

        final ForecastHolder holder = new ForecastHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ForecastHolder holder, int position) {

        WeatherData weatherData = data.get(position);

        holder.temperature.setText(String.format("%s °C", weatherData.getMain().getTemp()));
        holder.weather.setText(weatherData.getWeather().get(0).getDescription());
        holder.date.setText(weatherData.getDate());

        Glide.with(holder.getContext()).load(makeImageUrl(weatherData.getWeather().get(0).getIcon()))
                .into(holder.weatherIcon);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private String makeImageUrl(String weatherIcon) {
        return "http://openweathermap.org/img/wn/" + weatherIcon + "@2x.png";
    }

    static class ForecastHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_temperature)
        TextView temperature;

        @BindView(R.id.textView_weather)
        TextView weather;

        @BindView(R.id.textView_date)
        TextView date;

        @BindView(R.id.imageView_weather)
        ImageView weatherIcon;

        private View layout;

        ForecastHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            layout = v;
        }

        public Context getContext() {
            return layout.getContext();
        }
    }
}
