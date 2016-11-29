package com.example.andrewlane.forecast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewlane.forecast.fragments.MapViewFragment;
import com.example.andrewlane.forecast.model.Forecast;
import com.example.andrewlane.forecast.utils.ForecastReceivedEvent;
import com.example.andrewlane.forecast.utils.ForecastVolleyClient;
import com.example.andrewlane.forecast.utils.ImageRecievedEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_REQUEST_URL = "http://androidtutorialpoint.com/api/lg_nexus_5x";
    private static final String STRING_REQUEST_URL = "http://www.ncdc.noaa.gov/cdo-web/api/v2/locations?locationcategoryid=ZIP&sortfield=name&sortorder=desc";
    private static final String JSON_OBJECT_REQUEST_URL = "http://androidtutorialpoint.com/api/volleyJsonObject";
    private static final String JSON_ARRAY_REQUEST_URL = "http://androidtutorialpoint.com/api/volleyJsonArray";

    ProgressDialog progressDialog;
    private static final String TAG = "-------aaa";
    private Button JsonObjectRequestButton;
    private Button ImageRequestButton;
    private View showDialogView;
    private TextView outputTextView;
    private ImageView outputImageView;
    private ImageView imgView;
    SupportMapFragment mapViewFragment;
    private GoogleMap map;

    ForecastVolleyClient wh;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.MainLayout,
                        new MapViewFragment()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void doit(View view) {
        wh = new ForecastVolleyClient(null, "day", true, null);
        wh.makeForecastRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showForecast(ForecastReceivedEvent forecastReceivedEvent) {
        Forecast f = forecastReceivedEvent.getForecast();
        Log.d(TAG, "BUS@: " + forecastReceivedEvent.getType());

        //f.currentCondition.getIcon()
        Toast.makeText(this, f.location.getCity() + " ---- " + f.clouds.getPerc() + " ---- " + f.location.getSunrise(), Toast.LENGTH_LONG).show();

        //Here we should call another funciton which will process the image and the function below will do the work
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showIcons(ImageRecievedEvent imageRecievedEvent) {
        //Toast.makeText(this, f.location.getCity() + " ---- " + f.currentCondition.getIcon(), Toast.LENGTH_SHORT).show();

        //Here we should call another funciton which will process the image
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

}