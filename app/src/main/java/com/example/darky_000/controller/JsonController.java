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

/*
 * Created by abhijit on 12/2/16.
 */

/**
 * <p> Provides interface between {@link android.app.Activity} and {@link com.android.volley.toolbox.Volley} </p>
 */
public class JsonController {

    private final int TAG = 100;

    private OnResponseListener responseListener;

    /**
     * @param responseListener {@link OnResponseListener}
     */
  public JsonController(OnResponseListener responseListener) {
      this.responseListener = responseListener;
  }

    /**
     * Adds request to volley request queue
     *
     * @param query query term for search
     */
    public void sendRequest(String query) {

        // Request Method
        int method = Request.Method.GET;

        // Url with GET parameters
        String url = "https://api.meetup.com/find/events?key=" + Api.apikey + "&fields=group_photo";


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

   /**
    * <p>Cancels all request pending in request queue,</p>
    * <p> There is no way to control the request already processed</p>
    */
   public void cancelAllRequests() {
       VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
   }

//    /**
//     * Interface to communicate between {@link android.app.Activity} and {@link JsonRequest}
//     * <p>Object available in {@link JsonRequest} and implemented in {@link com.example.csc413_volley_template.MainActivity}</p>
//     */
  public interface OnResponseListener {
      void onSuccess(List<StoryEvent> storyEvents);

      void onFailure(String errorMessage);
  }

}