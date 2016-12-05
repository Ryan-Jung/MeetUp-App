package com.example.darky_000.story_finder;

import android.widget.ImageView;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darky_000 on 11/20/2016.
 */

public class Story {


    private UUID mUuid;
    private String mName;
    private String mDescription;
    private ImageView mImage;

    public Story() {
        mUuid = UUID.randomUUID();
    }

    public UUID getmUuid() {
        return mUuid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public ImageView getmImage() {
        return mImage;
    }

    public void setmImage(ImageView mImage) {
        this.mImage = mImage;
    }
}