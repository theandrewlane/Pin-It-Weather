package com.example.andrewlane.forecast;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by andrewlane on 9/25/16.
 */
public class Forecast {

    private static String temperature;
    private static Date startDate;
    private static Date endDate;
    private static String imageType;
    private static String wind;
    private static String dewPoint;
    //TODO other weather shit

    public static String res;

    public Forecast(JSONObject obj) {
        this.res = obj.toString();
    }

    public static String getRes() {
        return res;
    }


    public static String getTemperature() {
        return temperature;
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static String getImageType() {
        return imageType;
    }

    public static String getWind() {
        return wind;
    }

    public static String getDewPoint() {
        return dewPoint;
    }
}
