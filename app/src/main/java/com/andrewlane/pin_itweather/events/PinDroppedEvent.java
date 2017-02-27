package com.andrewlane.pin_itweather.events;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by andrewlane on 10/16/16.
 */

public class PinDroppedEvent {
    Location location;
    LatLng latLng;

    private String type;

    public PinDroppedEvent(LatLng coords) {
        this.latLng = coords;
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
    }

    public LatLng getLatLon() {
        return latLng;
    }
}