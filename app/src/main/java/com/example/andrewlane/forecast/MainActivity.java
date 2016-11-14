package com.example.andrewlane.forecast;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewlane.forecast.model.Forecast;
import com.example.andrewlane.forecast.utils.ForecastReceivedEvent;
import com.example.andrewlane.forecast.utils.ForecastVolleyClient;
import com.example.andrewlane.forecast.utils.ImageRecievedEvent;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_REQUEST_URL = "http://androidtutorialpoint.com/api/lg_nexus_5x";
    private static final String STRING_REQUEST_URL = "http://www.ncdc.noaa.gov/cdo-web/api/v2/locations?locationcategoryid=ZIP&sortfield=name&sortorder=desc";
    private static final String JSON_OBJECT_REQUEST_URL = "http://androidtutorialpoint.com/api/volleyJsonObject";
    private static final String JSON_ARRAY_REQUEST_URL = "http://androidtutorialpoint.com/api/volleyJsonArray";

    ProgressDialog progressDialog;
    private static final String TAG = "-------aaa";
    private Button JsonObjectRequestButton;
    private Button ImageRequestButton;
    private View showDialogView;
    private TextView outputTextView;
    private ImageView outputImageView;
    private ImageView imgView;

    ForecastVolleyClient wh;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        JsonObjectRequestButton = (Button) findViewById(R.id.button_get_Json_object);
        imgView = (ImageView) findViewById(R.id.condIcon);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void doit(View view) {
        wh = new ForecastVolleyClient(null, "day", true, null);
        wh.makeForecastRequest();
    }

@Subscribe(threadMode = ThreadMode.MAIN)
    public void showForecast(ForecastReceivedEvent forecastReceivedEvent) {
        Forecast f = forecastReceivedEvent.getForecast();
        Log.d(TAG, "BUS@: " + forecastReceivedEvent.getType());

    //f.currentCondition.getIcon()
        Toast.makeText(this, f.location.getCity() + " ---- " + f.clouds.getPerc()+ " ---- " + f.location.getSunrise(), Toast.LENGTH_LONG).show();

        //Here we should call another funciton which will process the image and the function below will do the work
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showIcons(ImageRecievedEvent imageRecievedEvent) {
        //Toast.makeText(this, f.location.getCity() + " ---- " + f.currentCondition.getIcon(), Toast.LENGTH_SHORT).show();

        //Here we should call another funciton which will process the image
    }


    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}