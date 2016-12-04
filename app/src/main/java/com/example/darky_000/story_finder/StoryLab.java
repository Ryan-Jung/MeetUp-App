package com.example.darky_000.story_finder;

import android.content.Context;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by darky_000 on 11/21/2016.
 */

public class StoryLab {
    private static StoryLab sLab;

    private ArrayList<Story> mStories;

    private StoryLab(Context context) {
        mStories = new ArrayList<>();
        createStory(context);

    }


    public static StoryLab get(Context context) {
        if (sLab == null) {
            sLab = new StoryLab(context);
        }
        return sLab;
    }

    public List<Story> getStories(String query) {
        ArrayList<Story> subsetStory = new ArrayList<>();

        /*if(query.isEmpty()) {
            return mStories;
        }*/

        for (Story story : mStories) {
            if (!query.isEmpty() && story.getmName().contains(query)) {
                subsetStory.add(story);
            }
        }

        return subsetStory;
    }

    public Story getStory(UUID id) {
        for (Story story : mStories) {
            if (story.getmUuid().equals(id)) {
                return story;
            }
        }
        return null;
    }
    private void createStory(Context context) {

        for (int i = 0; i < 5; i++) {
            Story story = new Story();
            story.setmName((i+1) + ": Name");
            story.setmDescription((i+1) + ": Description, etc");
            ImageView iv = new ImageView(context);
            iv.setImageResource(context.getResources().getIdentifier("drawable/image" + (i%5), null, context.getPackageName()));
            story.setmImage(iv);
            mStories.add(story);
            /*Toast.makeText(iv.getContext(),
                    story.getmUuid() + "clicked!", Toast.LENGTH_SHORT)
                    .show();*/
            System.out.println(story.getmUuid());
        }
    }

}