package com.andrewlane.pin_itweather.events;


import com.google.android.gms.maps.GoogleMap;

/**
 * Created by andrewlane on 10/16/16.
 */

public class MapReadyEvent {
    GoogleMap googleMap;


    public MapReadyEvent(GoogleMap gMap) {
        this.googleMap = gMap;
    }

    public GoogleMap getMap() {
        return googleMap;
    }
}