package com.example.andrewlane.forecast.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.andrewlane.forecast.MainActivity;
import com.example.andrewlane.forecast.app.AppController;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import static com.example.andrewlane.forecast.app.AppController.logTag;

/**
 * Created by andrewlane on 10/11/16.
 */

public class JSONRequest {

    public JSONRequest(String endpoint, String token, String params) {
        this.ENDPOINT = endpoint;
        this.TOKEN = token;
        this.PARAM = params;
    }

    private EventBus bus = EventBus.getDefault();

    private String logTag = "\n-----JSON REQUEST: ";
    private String tag_json_obj;
    private String ENDPOINT;
    private String TOKEN;
    private String PARAM = null;
    private String cancelReqID = "app-req";
    private MainActivity ma;


//    /**
//     * This function should alert the main activity that the api call has received
//     * a response and that the json object contains data
//     *
//     */
//    public JSONObject responseReceived(JSONObject res){
//        Log.d(logTag, "sending data to main activity. \nData: " + res);
//        ma.getData(res);
//    }

    /**
     * Making json object request
     */
    public void makeJsonObjReq(final VolleyCallback callback) {
        System.out.println("starting'");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                ENDPOINT + "&APPID=" + TOKEN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                        bus.postSticky(new responseRecievedEvent(response));
                        Log.d(logTag, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(logTag, error.toString());
                VolleyLog.d(logTag, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("appid", TOKEN);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                cancelReqID);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);

    }
}
