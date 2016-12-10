package com.example.darky_000;


import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by darky_000 on 12/4/2016.
 */
//The API Meetup Link is this
//https://api.meetup.com/find/events?key=783e741d1d3316397c7e3b2b175025b

public class StoryEvent {

    private static List<StoryEvent> storyEvents = new ArrayList<>();
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
    private String urlImage;
    private String who;
    private String id;
    private String city;
    private String link;
    private String description;
    private String email;
    private UUID uuid = UUID.randomUUID();


    private StoryEvent(){

    }

    public StoryEvent(int created, int duration, String id, String name, int rsvp_limit,
                      String status, int time, int updated, int utc, int waitlist,
                      int rsvp_count, int latitude, int longnitude, String urlImage,
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
        this.urlImage = urlImage;
        this.who = who;


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
            //String description = jsonObject.getString("description");
            Document document = Jsoup.parse(jsonObject.getString("description"));
            document.select("img").remove();
//            Log.i("SET DESCRIPTION", description );
            description = Html.fromHtml(document.toString()).toString();
            this.setDescription(description);


        }

        if (jsonObject.has("group") && jsonObject.getJSONObject("group").has("photo")) {
            this.setUrlImage(jsonObject.getJSONObject("group").getJSONObject("photo").
                    getString("photo_link"));
        }

        if(jsonObject.has("link")){
            this.setLink(jsonObject.getString("link"));
        }
        /**
         * Get data from venue JSON object
         */
        if(jsonObject.has("venue")){
           JSONObject venue = jsonObject.getJSONObject("venue");
            if(venue.has("lat") && venue.has("lon")){
                this.latitude = venue.getInt("lat");
                this.longnitude = venue.getInt("lon");
            }
            if(venue.has("city")){
                this.city = venue.getString("city");
            }
        }

    }

    public static List<StoryEvent> parseJson(JSONArray jsonArr) throws JSONException {

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.length(); i++) {
                if (i == 3) {
                    continue;
                }
                //  Create new Story object from each JSONObject in the JSONArray
                storyEvents.add(new StoryEvent(jsonArr.getJSONObject(i)));
            }
        }

        return storyEvents;
    }

    public static StoryEvent get() {
        StoryEvent storyEvent = new StoryEvent();
        if (storyEvent == null) {
            storyEvent = new StoryEvent();
        }
        return storyEvent;
    }

    public List<StoryEvent> getStoryEvents(String query) {
        ArrayList<StoryEvent> filteredEvents = new ArrayList<>();
        for (StoryEvent storyEvent : storyEvents) {
            if (!query.isEmpty() && storyEvent.getName().contains(query)) {
                filteredEvents.add(storyEvent);
            }
        }
        return filteredEvents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRsvp_limit() {
        return String.valueOf(rsvp_limit);
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

    private String getUrlFromDescription(String s){
        String url = "";
        if(s != null){
            Pattern pattern = Pattern.compile(
                    "(.*)(img src=\"(.*(jpeg|jpg|png))\")(.*)");
            Matcher matcher = pattern.matcher(s);
            if(s.contains("img src") && matcher.find()){
                url = matcher.group(3);
            }
        }
        return url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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


