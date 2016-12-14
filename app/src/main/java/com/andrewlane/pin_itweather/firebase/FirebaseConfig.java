package com.andrewlane.pin_itweather.firebase;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by andrewlane on 12/5/16.
 */

public class FirebaseConfig {

    public static final String FIREBASE_URL = "https://pin-it-weather.firebaseio.com/";


    public static String FIREBASE_TOKEN = FirebaseInstanceId.getInstance().getToken();


}
