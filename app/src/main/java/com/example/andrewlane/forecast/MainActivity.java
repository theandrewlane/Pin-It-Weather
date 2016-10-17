package com.example.andrewlane.forecast;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrewlane.forecast.utils.responseRecievedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private EventBus bus = EventBus.getDefault();

    private static final String IMAGE_REQUEST_URL = "http://androidtutorialpoint.com/api/lg_nexus_5x";
    private static final String STRING_REQUEST_URL = "http://www.ncdc.noaa.gov/cdo-web/api/v2/locations?locationcategoryid=ZIP&sortfield=name&sortorder=desc";
    private static final String JSON_OBJECT_REQUEST_URL = "http://androidtutorialpoint.com/api/volleyJsonObject";
    private static final String JSON_ARRAY_REQUEST_URL = "http://androidtutorialpoint.com/api/volleyJsonArray";

    ProgressDialog progressDialog;
    private static final String TAG = "MainActivity";
    private Button JsonObjectRequestButton;
    private Button ImageRequestButton;
    private View showDialogView;
    private TextView outputTextView;
    private ImageView outputImageView;
    WeatherHelper wh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        JsonObjectRequestButton = (Button) findViewById(R.id.button_get_Json_object);
    }


    public void doit(View view) {
        wh = new WeatherHelper(null, "day", true, null);
        wh.makeWeatherRequest();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void getData(JSONObject res) {
        Log.d(TAG, "getData: " + res);
    }


    /**
     * Receiving Login event when it happens
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void responseRecievedEvent(responseRecievedEvent event) {
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        showDialogView = li.inflate(R.layout.show_dialog, null);
        outputTextView = (TextView) showDialogView.findViewById(R.id.text_view_dialog);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(showDialogView);
        alertDialogBuilder
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setCancelable(false)
                .create();
        outputTextView.setText(event.jsonObject.toString());
        alertDialogBuilder.show();
        progressDialog.hide();
        outputTextView = (TextView) showDialogView.findViewById(R.id.text_view_dialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this); // registering the bus
    }

    @Override
    public void onStop() {
        bus.unregister(this); // un-registering the bus
        super.onStop();
    }
}
//

//    public void volleyJsonObjectRequest(String url) {
//
//        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//
//                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
//                        showDialogView = li.inflate(R.layout.show_dialog, null);
//                        outputTextView = (TextView) showDialogView.findViewById(R.id.text_view_dialog);
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                        alertDialogBuilder.setView(showDialogView);
//                        alertDialogBuilder
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                    }
//                                })
//                                .setCancelable(false)
//                                .create();
//                        alertDialogBuilder.show();
//                        progressDialog.hide();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                progressDialog.hide();
//            }
//        });
//
//        // Adding JsonObject request to request queue
//        RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
//    }
//
//    public void volleyJsonArrayRequest(String url) {
//
//        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonArrayRequest";
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
//                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
//                        showDialogView = li.inflate(R.layout.show_dialog, null);
//                        outputTextView = (TextView) showDialogView.findViewById(R.id.text_view_dialog);
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                        alertDialogBuilder.setView(showDialogView);
//                        alertDialogBuilder
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                    }
//                                })
//                                .setCancelable(false)
//                                .create();
//                        outputTextView.setText(response.toString());
//                        alertDialogBuilder.show();
//                        progressDialog.hide();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                progressDialog.hide();
//            }
//        });
//
//        // Adding JsonObject request to request queue
//        RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
//    }
//
//    public void volleyImageLoader(String url) {
//        ImageLoader imageLoader = RequestSingleton.getInstance(getApplicationContext()).getImageLoader();
//
//        imageLoader.get(url, new ImageLoader.ImageListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Image Load Error: " + error.getMessage());
//            }
//
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
//                if (response.getBitmap() != null) {
//
//                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
//                    showDialogView = li.inflate(R.layout.show_dialog, null);
//                    outputImageView = (ImageView) showDialogView.findViewById(R.id.image_view_dialog);
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                    alertDialogBuilder.setView(showDialogView);
//                    alertDialogBuilder
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                }
//                            })
//                            .setCancelable(false)
//                            .create();
//                    outputImageView.setImageBitmap(response.getBitmap());
//                    alertDialogBuilder.show();
//                }
//            }
//        });
//    }
//
