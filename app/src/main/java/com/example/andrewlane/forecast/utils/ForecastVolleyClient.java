package com.example.andrewlane.forecast.utils;

import com.example.andrewlane.forecast.model.Forecast;

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

    private static final String apiURL = "http://api.openweathermap.org/data/2.5/weather?q=London";
    //    private static final String apiToken = "sPOCtHPDQWSPzBLPKEjuUulUUQotaViW";
    private static final String apiToken = "a156ffeca825ea08a1eb6fc913257d83";
    private String REQUEST_TAG = "com.andrewlane.noaa-forcast";

    private boolean subscribe; //This flag can subscribe to push alerts

    private JSONRequest req;
    private String geoCoord;
    Forecast forecast;
    ForecastParser forecastParser;


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

    public ForecastVolleyClient(String geoCoord, String type, Boolean subscribe, JSONRequest req) {
        this.geoCoord = geoCoord;
        this.type = type;
        this.subscribe = subscribe;
        this.req = req;
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
    private Forecast getDayForecast() {
        req = new JSONRequest(apiURL, apiToken, null);
        req.makeJsonObjReq(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    forecast = forecastParser.getForecast(result);
                    EventBus.getDefault().post(new ForecastReceivedEvent(forecast, type));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Call main activity here
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

