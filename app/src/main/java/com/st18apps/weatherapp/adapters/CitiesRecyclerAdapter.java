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

import static com.st18apps.weatherapp.utils.StringUtil.makeImageUrl;

public class CitiesRecyclerAdapter extends RecyclerView.Adapter<CitiesRecyclerAdapter.CitiesHolder> {
    private List<WeatherData> data;
    private ItemClickListener itemClickListener;
    private boolean currentCity = false;

    public CitiesRecyclerAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<WeatherData> getData() {
        return data;
    }

    public void setData(List<WeatherData> data) {
        this.data = data;
        currentCity = false;
        notifyDataSetChanged();
    }

    public void addCurrentCity(WeatherData weatherData) {
        data.add(0, weatherData);
        currentCity = true;
        notifyDataSetChanged();
    }

    public void addItem(WeatherData weatherData) {
        data.add(weatherData);
        currentCity = false;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        data.remove(position);
        currentCity = false;
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public CitiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        final CitiesHolder holder = new CitiesHolder(view);

        holder.layout.setOnClickListener(view1 ->
                itemClickListener.onItemClick(holder.getAdapterPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CitiesHolder holder, int position) {

        if (currentCity && position == 0)
            holder.currentCity.setVisibility(View.VISIBLE);
        else
            holder.currentCity.setVisibility(View.GONE);

        WeatherData weatherData = data.get(position);

        holder.city.setText(weatherData.getName());
        holder.temperature.setText(String.format("%s °C", weatherData.getTemp()));

        Glide.with(holder.getContext()).load(makeImageUrl(weatherData.getIcon()))
                .into(holder.imageWeather);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    static class CitiesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_name)
        TextView city;

        @BindView(R.id.textView_your_city)
        TextView currentCity;

        @BindView(R.id.textView_temperature)
        TextView temperature;

        @BindView(R.id.imageView_weather)
        ImageView imageWeather;

        private View layout;

        CitiesHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            layout = v;
        }

        public Context getContext() {
            return layout.getContext();
        }
    }
}
