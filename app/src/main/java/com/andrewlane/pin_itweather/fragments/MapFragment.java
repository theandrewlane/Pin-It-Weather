package com.andrewlane.pin_itweather.fragments;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewlane.pin_itweather.MainActivity;
import com.andrewlane.pin_itweather.R;
import com.andrewlane.pin_itweather.events.MapReadyEvent;
import com.andrewlane.pin_itweather.events.PinDroppedEvent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    MapView mapView;
    GoogleMap map;
    MainActivity ma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapFrag);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setOnMapLongClickListener(this);
        map.getUiSettings().setAllGesturesEnabled(true);
        LatLng userLocation;
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        EventBus.getDefault().post(new MapReadyEvent(map));


        //IF we can't get location, use SLC as the default
         Location location = service.getLastKnownLocation(provider);
        if(location != null) {
            map.setMyLocationEnabled(true);
             userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 12);
            map.animateCamera(cameraUpdate);
        } else {
            userLocation = new LatLng(40.76, -111.89);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 12);
            map.animateCamera(cameraUpdate);
        }


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        map.clear();
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
        EventBus.getDefault().post(new PinDroppedEvent(latLng));
    }
}