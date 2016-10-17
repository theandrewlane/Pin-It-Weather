package com.example.andrewlane.forecast;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.andrewlane.forecast.utils.JSONRequest;
import com.example.andrewlane.forecast.utils.VolleyCallback;
import com.example.andrewlane.forecast.utils.responseRecievedEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static android.content.ContentValues.TAG;

/**
 * Created by andrewlane on 9/25/16.
 * <p>
 * This class should be used to abstract the calling of weather APIs needed for the main activity
 */

public class WeatherHelper {

    private static final String apiURL = "http://api.openweathermap.org/data/2.5/weather?q=London";
    //    private static final String apiToken = "sPOCtHPDQWSPzBLPKEjuUulUUQotaViW";
    private static final String apiToken = "a156ffeca825ea08a1eb6fc913257d83";
    private String REQUEST_TAG = "com.andrewlane.noaa-forcast";

    private boolean subscribe; //This flag can subscribe to push alerts

    private JSONRequest req;
    private String geoCoord;
    Forecast forecast;


    private String type; //Implementation TBD
    boolean isSubscibed;

//    public List<?> getParam() {
//        return params;
//    }
//
//    public void setParam(ArrayList<?> params) {
//        params.add(geoCoord);
//        this.params = params;
//    }

    private List<?> params;

    public WeatherHelper(String geoCoord, String type, Boolean subscribe, JSONRequest req) {
        this.geoCoord = geoCoord;
        this.type = type;
        this.subscribe = subscribe;
        this.req = req;
    }


    //Based on the required request type, call the right function.
    public void makeWeatherRequest() {
        switch (type) {
            case "week":
                getWeekForecast();
                break;
            case "day":
                getDayForcast();
                break;
            case "sub":
                subScribeLocationNotification();
                break;
        }
    }

    //TODO - subscribe to the res callback of makeJsonObjReq in JSONReq
    private Forecast getDayForcast() {
        req = new JSONRequest(apiURL, apiToken, null);
        req.makeJsonObjReq(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                forecast = new Forecast(result);
                //Call main activity here
                //bus.postSticky(new responseRecievedEvent(forecast));
            }
        });
        return forecast;
    }


    public Forecast getWeekForecast() {
        return forecast;
    }

    public boolean subScribeLocationNotification() {
        return true;
    }
}

