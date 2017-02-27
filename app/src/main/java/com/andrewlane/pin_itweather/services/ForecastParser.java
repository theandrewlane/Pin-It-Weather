package com.andrewlane.pin_itweather.services;

import com.andrewlane.pin_itweather.model.Forecast;
import com.andrewlane.pin_itweather.model.Location;

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
        JSONObject sysObj = getObject("sys", jObj);
        JSONObject coordObj = getObject("coord", jObj);
        loc.setLatitude(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

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
        forecast.currentCondition.setIcon(setWeatherIcon(getString("icon", JSONWeather)));


        JSONObject mainObj = getObject("main", jObj);
        forecast.currentCondition.setHumidity(getInt("humidity", mainObj));
        forecast.currentCondition.setPressure(getInt("pressure", mainObj));
        forecast.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        forecast.temperature.setMinTemp(getFloat("temp_min", mainObj));
        forecast.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        forecast.wind.setSpeed(getString("speed", wObj));
        forecast.wind.setDeg(getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jObj);
        forecast.clouds.setPerc(getInt("all", cObj));

        // We download the icon to show

        return forecast;
    }

    private static String setWeatherIcon(String icon) {
        String drawableName;
        switch (icon) {
            case "01d":
                drawableName = "wi_day_cloudy";
                break;
            case "01n":
                drawableName = "wi_night_clear";
                break;
            case "02d":
            case "02n":
                drawableName = "wi_day_cloudy";
                break;
            case "03d":
            case "04d":
                drawableName = "wi_day_cloudy";
                break;
            case "03n":
            case "04n":
                drawableName = "wi_night_cloudy";
                break;
            case "09d":
            case "09n":
                drawableName = "wi_day_showers";
                break;
            case "10d":
            case "10n":
                drawableName = "wi_day_rain";
                break;
            case "11d":
            case "11n":
                drawableName = "wi_day_thunderstorm";
                break;
            case "13d":
            case "13n":
                drawableName = "wi_day_snow";
                break;
            default:
                drawableName = "wi_wu_clear";
        }
        return drawableName;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private static long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }

}


