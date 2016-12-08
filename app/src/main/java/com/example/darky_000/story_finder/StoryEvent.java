package com.example.darky_000.story_finder;


import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by darky_000 on 12/4/2016.
 */
//The API Meetup Link is this
//https://api.meetup.com/find/events?key=783e741d1d3316397c7e3b2b175025b

public class StoryEvent {

    private int created;
    private int duration;
    private String name;
    private int rsvp_limit;
    private String status;
    private int time;
    private int updated;
    private int utc;
    private int waitlist;
    private int rsvp_count;

    private int latitude;
    private int longnitude;
    private String urlname;
    private String who;
    private String id;

    private String link;
    private String description;
    private String email;

    private UUID uuid = UUID.randomUUID();


    private StoryEvent(){

    }

    public StoryEvent(int created, int duration, String id, String name, int rsvp_limit,
                      String status, int time, int updated, int utc, int waitlist,
                      int rsvp_count, int latitude, int longnitude, String urlname,
                      String who, String link, String description, String email,
                      String visibility){
        this.created = created;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.rsvp_limit = rsvp_limit;
        this.status = status;
        this.time = time;
        this.updated = updated;
        this.utc = utc;
        this.waitlist = waitlist;
        this.rsvp_count = rsvp_count;

        this.link = link;
        this.description = description;
        this.email = email;

        this.latitude = latitude;
        this.longnitude = longnitude;
        this.urlname = urlname;
        this.who = who;


    }
    public static List<StoryEvent> parseJson(JSONArray jsonArr) throws JSONException{
        List<StoryEvent> storyEvents = new ArrayList<>();

        if(jsonArr != null) {
            for (int i = 0; i < jsonArr.length(); i++) {
                if(i == 3){continue;}
                //  Create new Story object from each JSONObject in the JSONArray
                storyEvents.add(new StoryEvent(jsonArr.getJSONObject(i)));
            }
        }

        return storyEvents;
    }

    private StoryEvent(JSONObject jsonObject) throws JSONException {

        if(jsonObject.has("name")){
            this.setName(jsonObject.getString("name"));
        }
        if(jsonObject.has("id")){
            this.setId(jsonObject.getString("id"));
        }
        if(jsonObject.has("yes_rsvp_count")){
            this.setRsvp_limit(jsonObject.getInt("yes_rsvp_count"));
        }
        if(jsonObject.has("description")){
            String description = jsonObject.getString("description");
            Log.i("SET DESCRIPTION", description );
            description = Html.fromHtml(description).toString();
            this.setDescription(description);
        }
        if(jsonObject.has("link")){
            this.setLink(jsonObject.getString("link"));
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRsvp_limit() {
        return rsvp_limit;
    }

    public void setRsvp_limit(int rsvp_limit) {
        this.rsvp_limit = rsvp_limit;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongnitude() {
        return longnitude;
    }

    public void setLongnitude(int longnitude) {
        this.longnitude = longnitude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getmUuid(){
        return uuid;
    }
}


