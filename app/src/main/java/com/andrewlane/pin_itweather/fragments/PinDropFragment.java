package com.andrewlane.pin_itweather.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andrewlane.pin_itweather.MainActivity;
import com.andrewlane.pin_itweather.R;
import com.andrewlane.pin_itweather.events.MapReadyEvent;
import com.andrewlane.pin_itweather.events.PinDroppedEvent;
import com.andrewlane.pin_itweather.events.VolleyCallback;
import com.andrewlane.pin_itweather.model.Forecast;
import com.andrewlane.pin_itweather.services.ForecastParser;
import com.andrewlane.pin_itweather.services.ForecastVolleyClient;
import com.andrewlane.pin_itweather.services.JSONRequest;
import com.andrewlane.pin_itweather.views.WeatherTextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

public class PinDropFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    JSONObject jsonWeather;
    private MapFragment mapFragment;
    private TextView windSpeed;
    private TextView humidity;
    private TextView temp;
    private TextView lowTemp;
    private TextView highTemp;
    private TextView sunrise;
    private TextView sunset;
    private TextView pressure;
    private TextView currentCondiiton;
    private TextView city;
    SaveWeatherFragment dialogFragment;

    private ForecastVolleyClient forecastVolleyClient;
    public Forecast forecast;
    private Location location;
    private LatLng latLng;
    String token;
    private View rv;
    private MainActivity ma;
    private Typeface weatherFont;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private CameraUpdate center;
    private Activity mActivity;

    CameraUpdate zoom;
    private WeatherTextView weatherTextView;
    private GoogleApiClient googleApiClient;

    public PinDropFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ma = (MainActivity) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapFragment = new MapFragment();
         dialogFragment = new SaveWeatherFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.mapContainer, mapFragment);
        transaction.commit();
        // Inflate the layout for this fragment
        View rv = inflater.inflate(R.layout.fragment_pin_drop, container, false);
        windSpeed = (TextView) rv.findViewById(R.id.tvWind);
        humidity = (TextView) rv.findViewById(R.id.tvHumidity);
        highTemp = (TextView) rv.findViewById(R.id.tvTempHigh);
        lowTemp = (TextView) rv.findViewById(R.id.tvTempLow);
        pressure = (TextView) rv.findViewById(R.id.tvPressure);
        currentCondiiton = (TextView) rv.findViewById(R.id.tvCurrentCondition);
        city = (TextView) rv.findViewById(R.id.tvCity);
        temp = (TextView) rv.findViewById(R.id.tvTemperature);
        weatherTextView = (WeatherTextView) rv.findViewById(R.id.weather_icon);
        return rv;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pin_drop, menu);
    }

    @Override
    public void onAttach(Activity act) {
        super.onAttach(act);
        this.mActivity = act;
    }


    @Subscribe()
    public void moveMap(MapReadyEvent mapReadyEvent) {
        mMap = mapReadyEvent.getMap();
    }


    @Subscribe()
    public void getWeather(PinDroppedEvent pinDroppedEvent) {
        latLng = pinDroppedEvent.getLatLon();
        forecastVolleyClient = new ForecastVolleyClient(latLng, true);
        JSONRequest req = forecastVolleyClient.createForecastRequest(forecastVolleyClient.queryURL(latLng));
        req.makeJsonObjReq(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                updateWeather(result);
            }
        });
    }



    public void onFinishUserDialog(String user) {
        Toast.makeText(ma, "Hello, " + user, Toast.LENGTH_SHORT).show();
    }

    private void updateWeather(JSONObject result) {
        try {
            forecast = ForecastParser.getForecast(result);
            dialogFragment.setForecast(forecast);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String.valueOf(forecast.location.getLatitude() + forecast.location.getLongitude());
        windSpeed.setText(String.valueOf(forecast.wind.getSpeed()));
        temp.setText(String.valueOf(forecast.temperature.getTemp()));
        humidity.setText(String.valueOf(forecast.currentCondition.getHumidity()));
        weatherTextView.setText(forecast.currentCondition.getCondition());

        humidity.setText(String.valueOf(forecast.currentCondition.getHumidity()));
        highTemp.setText(String.valueOf(forecast.temperature.getMaxTemp()));
        lowTemp.setText(String.valueOf(forecast.temperature.getMinTemp()));

        pressure.setText(String.valueOf(forecast.currentCondition.getPressure()));
        currentCondiiton.setText(String.valueOf(forecast.currentCondition.getDescr()));
        city.setText(String.valueOf(forecast.location.getCity()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                FragmentManager fm = getActivity().getFragmentManager();
                dialogFragment.show(fm, "Save Tag");
                break;
            case R.id.reset:
                //add the function to perform here
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public Forecast getForecast(){
        return forecast;
    }
}
