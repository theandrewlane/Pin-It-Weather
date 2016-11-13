package com.example.andrewlane.forecast.utils;

import com.example.andrewlane.forecast.model.Forecast;

/**
 * Created by andrewlane on 10/16/16.
 */

public class ImageRecievedEvent {
    private Forecast forecast;


    private String type;

//    public ImageRecievedEvent(Image, String type){
//        this.forecast = f;
//        this.type = type;
//    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}