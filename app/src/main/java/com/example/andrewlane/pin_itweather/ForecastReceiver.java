package com.example.andrewlane.pin_itweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by andrewlane on 11/12/16.
 */

public class ForecastReceiver extends BroadcastReceiver {

    private EventBus bus = EventBus.getDefault();


    @Override
    public void onReceive(Context context, Intent intent) {
        ForecastReceivedEvent event = null;



    }
}
