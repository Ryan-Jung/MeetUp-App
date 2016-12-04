package com.example.darky_000.story_finder;

import android.support.v4.app.Fragment;

/**
 * Created by darky_000 on 11/21/2016.
 */

public class StoryListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new StoryListFragment();
    }
}