package com.andrewlane.pin_itweather.model;

/**
 * Created by andrewlane on 12/14/16.
 */

public class WeatherOverview {
    String currentTemperature;
    String location;
    String locationName;
    String currentUser;
    String currentCondition;

    public  WeatherOverview(){}

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


    public WeatherOverview(String currentUser, String currentTemperature, String locationName, String currentCondition) {
        this.currentUser = currentUser;
        this.currentTemperature = currentTemperature;
        this.locationName = locationName;
        this.currentCondition = currentCondition;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getLocation() {
        return locationName;
    }

    public void setLocation(String location) {
        this.locationName = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
