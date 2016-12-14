package com.andrewlane.pin_itweather.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andrewlane.pin_itweather.R;
import com.andrewlane.pin_itweather.model.Forecast;
import com.andrewlane.pin_itweather.model.WeatherOverview;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SaveWeatherFragment extends DialogFragment implements TextView.OnEditorActionListener, View.OnClickListener {

    private LatLng latLng;
    private Forecast forecast;
    private EditText mEditText;
    private Button saveButton;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mChildRef = mRootRef.child("condition");
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String longitude;
    private String latitude;
    WeatherOverview weatherOverview;
    String currentTemperature;
    String location;
    String locationName;
    String currentUser;
    String currentCondition;


    private DatabaseReference mDatabaseReference;


    @Override
    public void onClick(View view) {

        weatherOverview = new WeatherOverview(mEditText.getText().toString(), currentTemperature, locationName, currentCondition);
        mDatabaseReference.child("users").child("andrew").child("weather").push().setValue(weatherOverview);
        this.dismiss();

    }

    public void setForecast(Forecast fc) {
        this.forecast = fc;
        this.latitude = String.valueOf(fc.location.getLatitude());
        this.longitude = String.valueOf(fc.location.getLongitude());
        this.locationName = fc.location.getCity();
        this.currentTemperature = String.valueOf(fc.temperature.getTemp());
        this.currentCondition = fc.currentCondition.getDescr();
    }

    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public SaveWeatherFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_weather_fragment, container);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mEditText = (EditText) view.findViewById(R.id.pinTitle);
        saveButton = (Button) view.findViewById(R.id.save);
        database = FirebaseDatabase.getInstance();
        mEditText.setOnEditorActionListener(this);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Name this Pin....");
        saveButton.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        // Return input text to activity
        UserNameListener activity = (UserNameListener) getActivity();
        activity.onFinishUserDialog(mEditText.getText().toString());
        mChildRef.setValue(mEditText.getText().toString());
        this.dismiss();
        return true;
    }
}