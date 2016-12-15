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

/**
 * Created by darky_000 on 12/4/2016.
 */
//The API Meetup Link is this
//https://api.meetup.com/find/events?key=783e741d1d3316397c7e3b2b175025b

public class StoryEvent {

    private static List<StoryEvent> storyEvents = new ArrayList<>();

    private String name;
    private int rsvp_limit;

    private int latitude;
    private int longnitude;
    private String urlImage;

    private String id;
    private String link;
    private String description;
    private UUID uuid = UUID.randomUUID();


    private StoryEvent(){

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
            //remove <img> tags
            document.select("img").remove();
            //remove "<a> tags
            document.select("a").remove();
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

    }

    public static List<StoryEvent> parseJson(JSONArray jsonArr) throws JSONException {

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.length(); i++) {
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
        if(query.isEmpty()){
            return storyEvents;
        }
        for (StoryEvent storyEvent : storyEvents) {
            if (!query.isEmpty() && storyEvent.getName().contains(query)) {
                filteredEvents.add(storyEvent);
            }
        }
        return filteredEvents;
    }

    public StoryEvent getStoryEvent(UUID uuid) {
        StoryEvent storyEvent = null;

        if (storyEvents != null) {
            for (StoryEvent story : storyEvents) {
                if (story.getmUuid().equals(uuid)) {
                    storyEvent = story;
                }
            }
        }
        return storyEvent;
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

    public String getRsvp_limit() {
        return String.valueOf(rsvp_limit);
    }

    public void setRsvp_limit(int rsvp_limit) {
        this.rsvp_limit = rsvp_limit;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public UUID getmUuid(){
        return uuid;
    }
}


