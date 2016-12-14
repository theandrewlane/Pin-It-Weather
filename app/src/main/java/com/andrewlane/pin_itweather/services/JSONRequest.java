package com.andrewlane.pin_itweather.services;

import android.util.Log;

import com.andrewlane.pin_itweather.AppController;
import com.andrewlane.pin_itweather.events.VolleyCallback;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrewlane on 10/11/16.
 */

public class JSONRequest {

    public JSONRequest(String endpoint, String token, String params) {
        this.ENDPOINT = endpoint;
        this.TOKEN = token;
        String PARAM = params;
    }

    private String logTag = "\n-----JSON REQUEST: ";
    private String tag_json_obj;
    private String ENDPOINT;
    private String TOKEN;


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
        String cancelReqID = "app-req";
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                cancelReqID);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);

    }
}
