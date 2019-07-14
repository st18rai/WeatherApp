package com.st18apps.weatherapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.st18apps.weatherapp.db.CitiesWeatherDatabase;
import com.st18apps.weatherapp.interfaces.CitiesWeatherDao;
import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.network.ApiClient;
import com.st18apps.weatherapp.network.responses.DetailWeatherResponse;
import com.st18apps.weatherapp.utils.RxUtil;

import java.util.List;

public class Repository {
    private final String UNITS = "metric";
    private final String LANG = "ru";

    private CitiesWeatherDao weatherDao;

    private LiveData<List<WeatherData>> savedWeatherData;

    private final MutableLiveData<WeatherData> cityWeather = new MutableLiveData<>();
    private final MutableLiveData<WeatherData> currentCityWeather = new MutableLiveData<>();
    private final MutableLiveData<DetailWeatherResponse> detailCityWeather = new MutableLiveData<>();
    private final MutableLiveData<List<WeatherData>> citiesWeather = new MutableLiveData<>();

    public Repository(Application application) {

        CitiesWeatherDatabase database = CitiesWeatherDatabase.getInstance(application);
        weatherDao = database.citiesWeatherDao();
        savedWeatherData = weatherDao.getAllWeatherData();
    }

    // for one city
    public LiveData<WeatherData> getCityWeather() {
        return cityWeather;
    }

    private void setCityWeather(WeatherData data) {
        cityWeather.setValue(data);
    }

    // for current user city
    private void setCurrentCityWeather(WeatherData data) {
        currentCityWeather.setValue(data);
    }

    public LiveData<WeatherData> getCurrentCityWeather() {
        return currentCityWeather;
    }


    // for several cities
    public LiveData<List<WeatherData>> getCitiesWeather() {
        return citiesWeather;
    }

    private void setCitiesWeather(List<WeatherData> dataList) {
        citiesWeather.setValue(dataList);
    }

    // detail weather
    public LiveData<DetailWeatherResponse> getDetailCityWeather() {
        return detailCityWeather;
    }

    private void setDetailCityWeather(DetailWeatherResponse data) {
        detailCityWeather.setValue(data);
    }


    // server methods
    public void loadCityWeather(String city) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getCityWeatherByName(city,
                UNITS, LANG, ApiClient.APP_ID), weatherData -> {

            if (!weatherData.isError()) {
                setCityWeather(weatherData);
                insert(weatherData);
            }

        }, throwable -> {
            setCityWeather(null);
            throwable.printStackTrace();
        });
    }

    public void loadCurrentCityWeather(double lat, double lon) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getCityWeatherByCoordinates(lat, lon,
                UNITS, LANG, ApiClient.APP_ID), weatherData -> {

            if (!weatherData.isError()) {
                setCurrentCityWeather(weatherData);
            }

        }, throwable -> {
            setCurrentCityWeather(null);
            throwable.printStackTrace();
        });
    }

    public void loadCitiesWeather(String citiesId) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getCitiesWeather(citiesId,
                UNITS, LANG, ApiClient.APP_ID), baseListResponse -> {

            if (!baseListResponse.isError()) {
                setCitiesWeather(baseListResponse.getWeatherDataList());
            }

        }, Throwable::printStackTrace);
    }

    public void loadDetailCityWeather(String cityId) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getDetailWeather(cityId,
                UNITS, LANG, ApiClient.APP_ID), detailWeatherResponse -> {

            if (!detailWeatherResponse.isError()) {
                setDetailCityWeather(detailWeatherResponse);
            }

        }, Throwable::printStackTrace);

    }


    // db methods
    public LiveData<List<WeatherData>> getSavedWeatherData() {
        return savedWeatherData;
    }

    public void insert(WeatherData weatherData) {
        new InsertWeatherAsyncTask(weatherDao).execute(weatherData);
    }

    public void update(WeatherData weatherData) {
        new UpdateWeatherAsyncTask(weatherDao).execute(weatherData);
    }

    public void delete(WeatherData weatherData) {
        new DeleteWeatherAsyncTask(weatherDao).execute(weatherData);
    }

    private static class InsertWeatherAsyncTask extends AsyncTask<WeatherData, Void, Void> {

        private CitiesWeatherDao dao;

        public InsertWeatherAsyncTask(CitiesWeatherDao citiesWeatherDao) {
            this.dao = citiesWeatherDao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            dao.insert(weatherData[0]);
            return null;
        }
    }

    private static class UpdateWeatherAsyncTask extends AsyncTask<WeatherData, Void, Void> {

        private CitiesWeatherDao dao;

        public UpdateWeatherAsyncTask(CitiesWeatherDao citiesWeatherDao) {
            this.dao = citiesWeatherDao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            dao.update(weatherData[0]);
            return null;
        }
    }

    private static class DeleteWeatherAsyncTask extends AsyncTask<WeatherData, Void, Void> {

        private CitiesWeatherDao dao;

        public DeleteWeatherAsyncTask(CitiesWeatherDao citiesWeatherDao) {
            this.dao = citiesWeatherDao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            dao.delete(weatherData[0]);
            return null;
        }
    }

}
