package com.example.andrewlane.forecast.utils;

import org.json.JSONObject;

/**
 * Created by andrewlane on 10/16/16.
 */

public class responseRecievedEvent {
    public final JSONObject jsonObject;

    public  responseRecievedEvent(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }
}