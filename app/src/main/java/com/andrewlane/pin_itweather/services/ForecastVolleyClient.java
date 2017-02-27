package com.andrewlane.pin_itweather.services;

import com.andrewlane.pin_itweather.events.VolleyCallback;
import com.andrewlane.pin_itweather.events.ForecastReceivedEvent;
import com.andrewlane.pin_itweather.model.Forecast;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by andrewlane on 9/25/16.
 * <p>
 * This class should be used to abstract the calling of weather APIs needed for the main activity
 */

public class ForecastVolleyClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    private static final String apiToken = "a156ffeca825ea08a1eb6fc913257d83";
    private String REQUEST_TAG = "com.andrewlane.pin_itWeather";

    private JSONRequest req;
    private String forcastURL;
    Forecast forecast;
    ForecastParser forecastParser;
    LatLng geoCoord;

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

    public ForecastVolleyClient(LatLng latLng, Boolean subscribe) {
        this.geoCoord = latLng;
        boolean subscribe1 = subscribe;
//        this.req = req;
    }


    //Based on the required request type, call the right function.
    public void makeForecastRequest() {
        switch (type) {
            case "week":
                getWeekForecast();
                break;
            case "day":
                getDayForecast();
                break;
            case "sub":
                subScribeLocationNotification();
                break;
        }
    }

    //TODO - subscribe to the res callback of makeJsonObjReq in JSONReq
    public Forecast getDayForecast() {
        forcastURL = queryURL(geoCoord);
        req = new JSONRequest(forcastURL, apiToken, null);
        req.makeJsonObjReq(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    EventBus.getDefault().post(new ForecastReceivedEvent(forecastParser.getForecast(result), type));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Call main activity here
            }
        });
        return forecast;
    }

    public Forecast mapViewUpdateForecast() {
        forcastURL = queryURL(geoCoord);
        req = new JSONRequest(forcastURL, apiToken, null);
        req.makeJsonObjReq(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    EventBus.getDefault().post(new ForecastReceivedEvent(forecastParser.getForecast(result), type));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Call main activity here
            }
        });
        return forecast;
    }

    public String queryURL(LatLng latLng) {
        String units = "&units=imperial";
        String apiURL = "http://api.openweathermap.org/data/2.5/weather?";
        return apiURL +"lat=" +latLng.latitude + "&lon=" + latLng.longitude + units;
    }

    public JSONRequest createForecastRequest(String reqUrl){
        return new JSONRequest(reqUrl, apiToken, null);

    }

    public Forecast getWeekForecast() {
        return forecast;
    }

    public boolean subScribeLocationNotification() {
        return true;
    }
}

