package com.example.darky_000.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.darky_000.StoryEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by darky_000 on 12/5/2016.
 */

public class JsonRequest extends Request<List<StoryEvent>> {
    // Success listener implemented in controller
    private Response.Listener<List<StoryEvent>> successListener;

    /**
     * Class constructor
     * @param method            Request method
     * @param url               url to API
     * @param successListener   success listener
     * @param errorListener     failure listener
     */
    public JsonRequest( int method,
                        String url,
                        Response.Listener<List<StoryEvent>> successListener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }



    @Override
    protected Response<List<StoryEvent>> parseNetworkResponse(NetworkResponse response) {
        // Convert byte[] data received in the response to String
        String jsonString = new String(response.data);
        List<StoryEvent> storyEvents;
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);
        // Try to convert JsonString to list of movies
        try {
            // Convert JsonString to JSONObject
            //jsonObject = new JSONObject(jsonString);
            JSONArray jsonArr = new JSONArray(jsonString);

            // Get list of events from received JSON
            storyEvents = StoryEvent.parseJson(jsonArr);
        }
        // in case of exception, return volley error
        catch (JSONException e) {
            e.printStackTrace();
            // return new volley error with message
            return Response.error(new VolleyError("Failed to process the request"));
        }
        // return list of movies
        return Response.success(storyEvents, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<StoryEvent> storyEvents) {
        successListener.onResponse(storyEvents);
    }

}
