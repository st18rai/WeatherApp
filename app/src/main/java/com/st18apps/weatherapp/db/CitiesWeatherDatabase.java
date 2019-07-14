package com.st18apps.weatherapp.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.st18apps.weatherapp.interfaces.CitiesWeatherDao;
import com.st18apps.weatherapp.interfaces.Constants;
import com.st18apps.weatherapp.model.WeatherData;

@Database(entities = WeatherData.class, version = 1)
public abstract class CitiesWeatherDatabase extends RoomDatabase {


    private static CitiesWeatherDatabase instance;

    public abstract CitiesWeatherDao citiesWeatherDao();

    public static synchronized CitiesWeatherDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CitiesWeatherDatabase.class, Constants.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new FillDatabase(instance).execute();
        }
    };

    private static class FillDatabase extends AsyncTask<Void, Void, Void> {

        CitiesWeatherDao weatherDao;

        public FillDatabase(CitiesWeatherDatabase db) {
            weatherDao = db.citiesWeatherDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            // Kiev data
            WeatherData kiev = new WeatherData(703448, 1563106145, "",
                    "Kiev", "пасмурно", "04d", 25);

            //Lviv data
            WeatherData lviv = new WeatherData(702550, 1563106145, "",
                    "Lviv", "дождь", "11d", 19);

            weatherDao.insert(kiev);
            weatherDao.insert(lviv);

            return null;
        }
    }
}
