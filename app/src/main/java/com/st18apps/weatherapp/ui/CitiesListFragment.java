package com.st18apps.weatherapp.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.st18apps.weatherapp.R;
import com.st18apps.weatherapp.adapters.CitiesRecyclerAdapter;
import com.st18apps.weatherapp.interfaces.Constants;
import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.utils.FragmentUtil;
import com.st18apps.weatherapp.viewmodels.ViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class CitiesListFragment extends BaseFragment implements CitiesRecyclerAdapter.ItemClickListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111;
    private static final String TAG = "wtf";

    @BindView(R.id.recycler_view_cities)
    RecyclerView recyclerViewCities;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ViewModel viewModel;
    private CitiesRecyclerAdapter adapter;

    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        setRecycler();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!checkPermissions()) {
            Log.i(TAG, "Inside onStart function; requesting permission when permission is not available");
            requestPermissions();
        } else {
            Log.i(TAG, "Inside onStart function; getting location when permission is already available");
            getLastLocation();

        }

        viewModel.getCitiesWeather("709930,703448,702550").observe(this, weatherData ->
                adapter.setData(weatherData));

    }

    private void setRecycler() {
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
                        addNewCity(editText.getText().toString());
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

    //Return whether permissions is needed as boolean value.
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    //Request permission from user
    private void requestPermissions() {
        Log.i(TAG, "Inside requestPermissions function");
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        //Log an additional rationale to the user. This would happen if the user denied the
        //request previously, but didn't check the "Don't ask again" checkbox.
        // In case you want, you can also show snackbar. Here, we used Log just to clear the concept.
        if (shouldProvideRationale) {
            Log.i(TAG, "****Inside requestPermissions function when shouldProvideRationale = true");
            startLocationPermissionRequest();
        } else {
            Log.i(TAG, "****Inside requestPermissions function when shouldProvideRationale = false");
            startLocationPermissionRequest();
        }
    }

    //Start the permission request dialog
    private void startLocationPermissionRequest() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    /**
     * Callback to the following function is received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // user interaction is cancelled; in such case we will receive empty grantResults[]
                //In such case, just record/log it.
                Log.i(TAG, "User interaction has been cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted by the user.
                Log.i(TAG, "User permission has been given. Now getting location");
                getLastLocation();
//                drawMap();
            } else {
                // Permission is denied by the user.
                Log.i(TAG, "User denied permission.");
            }
        }
    }

    /**
     * This method should be called after location permission is granted. It gets the recently available location,
     * In some situations, when location, is not available, it may produce null result.
     * WE used SuppressWarnings annotation to avoid the missing permission warning
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            currentLocation = task.getResult();
                            getCurrentCity(currentLocation);
                        } else {
                            Log.i(TAG, "Inside getLocation function. Error while getting location");
                            System.out.println(TAG + task.getException());
                        }

                    }
                });
    }

    private void getCurrentCity(Location location) {
        viewModel.getCityWeather(location.getLatitude(),
                location.getLongitude()).observe(CitiesListFragment.this, weatherData -> {

            if (weatherData != null && !isCityAlreadyExist(weatherData, adapter.getData()))
                adapter.addCurrentCity(weatherData);
        });
    }

    private void addNewCity(String city) {
        viewModel.getCityWeather(city).observe(this, weatherData -> {

            if (weatherData != null && !isCityAlreadyExist(weatherData, adapter.getData()))
                adapter.addItem(weatherData);
//                                else
//                                    Toast.makeText(getContext(), "Город уже в списке!", Toast.LENGTH_SHORT).show();

        });
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        showCityDialog();
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CITY_ID, adapter.getData().get(position).getId());

        FragmentUtil.replaceFragment(getFragmentManager(), new DetailCityFragment(),
                true, bundle);
    }
}
