package com.example.andrewlane.forecast.utils;
import com.example.andrewlane.forecast.model.Forecast;
import com.example.andrewlane.forecast.model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrewlane on 11/12/16.
 */

public class ForecastParser {
    public static Forecast getForecast(JSONObject jObj) throws JSONException {
        Forecast forecast = new Forecast();

        // We start extracting the info
        Location loc = new Location();

        JSONObject coordObj = getObject("coord", jObj);
        loc.setLatitude(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jObj);
        loc.setCountry(getString("country", sysObj));
        loc.setSunrise(getInt("sunrise", sysObj));
        loc.setSunset(getInt("sunset", sysObj));
        loc.setCity(getString("name", jObj));
        forecast.location = loc;

        // We get forecast info (This is an array)
        JSONArray jArr = jObj.getJSONArray("weather");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        forecast.currentCondition.setWeatherId(getInt("id", JSONWeather));
        forecast.currentCondition.setDescr(getString("description", JSONWeather));
        forecast.currentCondition.setCondition(getString("main", JSONWeather));
        forecast.currentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        forecast.currentCondition.setHumidity(getInt("humidity", mainObj));
        forecast.currentCondition.setPressure(getInt("pressure", mainObj));
        forecast.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        forecast.temperature.setMinTemp(getFloat("temp_min", mainObj));
        forecast.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        forecast.wind.setSpeed(getFloat("speed", wObj));
        forecast.wind.setDeg(getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jObj);
        forecast.clouds.setPerc(getInt("all", cObj));

        // We download the icon to show

        return forecast;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
    
}


