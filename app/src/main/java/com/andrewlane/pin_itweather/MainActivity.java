package com.andrewlane.pin_itweather;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.andrewlane.pin_itweather.adapters.ForecastRecyclerAdapter;
import com.andrewlane.pin_itweather.fragments.PinDropFragment;
import com.andrewlane.pin_itweather.fragments.SaveWeatherFragment;
import com.andrewlane.pin_itweather.model.Forecast;
import com.andrewlane.pin_itweather.model.WeatherOverview;
import com.andrewlane.pin_itweather.views.WeatherTextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    SaveWeatherFragment saveWeatherFragment;
    PinDropFragment pinDropFragment;
    Activity activity;

    private FloatingActionButton fab;

    ScaleAnimation shrinkAnim;
    private TextView tvNoWeather;

    private LinearLayoutManager mLinearLayoutManager;

    EventBus myEventBus;
    private RecyclerView mRecyclerView;
    private static final String userId = "53";
    private ForecastRecyclerAdapter forecastRecyclerAdapter;
    private ArrayList<Forecast> forecastList;

    private


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init recyclerview
        requestPermissions();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvNoWeather = (TextView) findViewById(R.id.no_Weather);
        shrinkAnim = new ScaleAnimation(1.15f, 0f, 1.15f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseRecyclerAdapter<WeatherOverview, WeatherDetailHolder> adapter = new FirebaseRecyclerAdapter<WeatherOverview, WeatherDetailHolder>(
                WeatherOverview.class,
                R.layout.weather_overview,
                WeatherDetailHolder.class,
                mDatabaseReference.child("users").child("andrew").child("weather").getRef()
        ) {
            @Override
            protected void populateViewHolder(WeatherDetailHolder viewHolder, WeatherOverview model, int position) {
                if (tvNoWeather.getVisibility() == View.VISIBLE) {
                    tvNoWeather.setVisibility(View.GONE);
                }
                viewHolder.city.setText(model.getLocation());
                viewHolder.currentCondiiton.setText(model.getCurrentCondition());
                viewHolder.temp.setText(model.getCurrentTemperature());
            }
        };

        mRecyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mRecyclerView.setVisibility(View.GONE);
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new PinDropFragment())
                        .addToBackStack(null)
                        .commit();
                //animation being used to make floating actionbar disappear
                shrinkAnim.setDuration(400);
                fab.setAnimation(shrinkAnim);
                shrinkAnim.start();
                shrinkAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //changing floating actionbar visibility to gone on animation end
                        fab.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200: {
                boolean locationPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    private boolean hasPermission(String permission) {
        return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    public boolean requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] perms = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.INTERNET"};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public static class WeatherDetailHolder extends RecyclerView.ViewHolder {

        TextView temp;
        TextView currentCondiiton;
        TextView city;
        WeatherTextView weatherTextView;

        public WeatherDetailHolder(View v) {
            super(v);
            currentCondiiton = (TextView) v.findViewById(R.id.tvCurrentCondition);
            city = (TextView) v.findViewById(R.id.tvCity);
            temp = (TextView) v.findViewById(R.id.tvTemperature);
            weatherTextView = (WeatherTextView) v.findViewById(R.id.weather_icon);
        }
    }

}
