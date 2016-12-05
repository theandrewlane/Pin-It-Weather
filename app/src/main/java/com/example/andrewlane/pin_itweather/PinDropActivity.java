package com.example.andrewlane.pin_itweather;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewlane.pin_itweather.model.Forecast;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class PinDropActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    private View rv;
    ForecastVolleyClient forecastVolleyClient;
    Forecast forecast;
    private JSONRequest req;
    Location location;
    LatLng latLng;

    Typeface weatherFont;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;

    WeatherIconView weatherIconView;


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

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pin_drop);
        windSpeed = (TextView) findViewById(R.id.tvWind);
        humidity = (TextView) findViewById(R.id.tvHumidity);
        highTemp = (TextView) findViewById(R.id.tvTempHigh);
        lowTemp = (TextView) findViewById(R.id.tvTempLow);
        sunrise = (TextView) findViewById(R.id.tvSunrise);
        sunset = (TextView) findViewById(R.id.tvSunset);
        pressure = (TextView) findViewById(R.id.tvPressure);
        currentCondiiton = (TextView) findViewById(R.id.tvCurrentCondition);
        city = (TextView) findViewById(R.id.tvCity);
        temp = (TextView) findViewById(R.id.tvTemperature);
        weatherIconView = (WeatherIconView) findViewById(R.id.weather_icon);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.

    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
    }

    //Getting current location
    private void getCurrentLocation() {
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);
        moveMap();
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        moveMap();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
//        EventBus.getDefault().post(new PinDroppedEvent(latLng));
        forecastVolleyClient = new ForecastVolleyClient(latLng, true);
        req = forecastVolleyClient.createForecastRequest(forecastVolleyClient.queryURL(latLng));
        req.makeJsonObjReq(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                updateWeather(result);
            }
        });

    }

    public void updateWeather(JSONObject result) {
        try {
            forecast = ForecastParser.getForecast(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        weatherFont = Typeface.createFromAsset(getAssets(), "weathericons-regular-webfont.ttf");
        String.valueOf(forecast.location.getLatitude() + forecast.location.getLongitude());
        windSpeed.append(String.valueOf(forecast.wind.getSpeed()));
        temp.append(String.valueOf(forecast.temperature.getTemp()));
        humidity.append(String.valueOf(forecast.currentCondition.getHumidity()));
        weatherIconView.setTypeface(weatherFont);
        weatherIconView.setText(Html.fromHtml(forecast.currentCondition.getIcon()));

        humidity.append(String.valueOf(forecast.currentCondition.getHumidity()));
        highTemp.append(String.valueOf(forecast.temperature.getMaxTemp()));
        lowTemp.append(String.valueOf(forecast.temperature.getMinTemp()));
        sunrise.append(String.valueOf(forecast.location.getSunrise()));
        sunset.append(String.valueOf(forecast.location.getSunset()));
        pressure.append(String.valueOf(forecast.currentCondition.getPressure()));
        currentCondiiton.append(String.valueOf(forecast.currentCondition.getDescr()));
        city.append(String.valueOf(forecast.location.getCity() + " , " + forecast.location.getCountry()));
        weatherIconView = (WeatherIconView) findViewById(R.id.weather_icon);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
