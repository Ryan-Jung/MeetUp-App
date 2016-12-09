package com.example.darky_000;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class StoryActivity extends SingleFragmentActivity {

    public static final String EXTRA_STORY_ID =
            "com.example.darky_000.story_finder";

    public static Intent newIntent(Context packageContext, UUID storyId) {
        Intent intent = new Intent(packageContext, StoryActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new StoryFragment();
    }

}