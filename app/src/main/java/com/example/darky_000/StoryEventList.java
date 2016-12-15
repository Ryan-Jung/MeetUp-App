package com.example.darky_000;

/**
 * Created on 12/6/2016.
 */


import java.util.List;
import java.util.UUID;

/**
 * Holds all StoryEvents (Meet ups) in a list
 */
public class StoryEventList {
    private static StoryEventList storyEventList;
    private List<StoryEvent> stories;

    private StoryEventList(){}

    public static StoryEventList getInstance(){
        if(storyEventList == null){
            storyEventList = new StoryEventList();
        }
        return storyEventList;
    }

    public void setList(List<StoryEvent> stories){
        this.stories = stories;
    }
}
