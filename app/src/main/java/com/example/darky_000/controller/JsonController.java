package com.example.darky_000.controller;

/**
 * Created by darky_000 on 12/5/2016.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.darky_000.Api;
import com.example.darky_000.StoryEvent;
import com.example.darky_000.app.App;
import com.example.darky_000.request.JsonRequest;
import com.example.darky_000.volley.VolleySingleton;

import java.util.List;

public class JsonController {

    private final int TAG = 100;

    private OnResponseListener responseListener;

    /**
     * @param responseListener {@link OnResponseListener}
     */
  public JsonController(OnResponseListener responseListener) {
      this.responseListener = responseListener;
  }
    
    public void sendRequest(String lat, String lon) {

        // Request Method
        int method = Request.Method.GET;

        // Url with GET parameters
        String url = "https://api.meetup.com/find/events?key=" + Api.apikey + "&fields=group_photo"
                + "&lat=" + lat + "&lon=" + lon;


        // Create new request using JsonRequest
      JsonRequest request
              = new JsonRequest(
              method,
              url,
              new Response.Listener<List<StoryEvent>>() {
                  @Override
                  public void onResponse(List<StoryEvent> response) {
                      responseListener.onSuccess(response);
                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      responseListener.onFailure(error.getMessage());
                  }
              });


              // Add tag to request
              request.setTag(TAG);

       // Get RequestQueue from VolleySingleton
       VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

  public interface OnResponseListener {
      void onSuccess(List<StoryEvent> storyEvents);

      void onFailure(String errorMessage);
  }

}