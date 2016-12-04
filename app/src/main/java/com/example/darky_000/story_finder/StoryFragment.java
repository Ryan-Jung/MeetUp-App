package com.example.darky_000.story_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.UUID;

/**
 * Created by darky_000 on 11/20/2016.
 */

public class StoryFragment extends Fragment {
    private Story mStory;
    private TextView mTitleField;
    private TextView mDetailField;
    private ImageView mImageField;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID storyId = (UUID) getActivity().getIntent()
                .getSerializableExtra(StoryActivity.EXTRA_STORY_ID);

        mStory = StoryLab.get(getActivity()).getStory(storyId);

        /*Toast.makeText(getActivity(),
                storyId.toString()+ "clicked!", Toast.LENGTH_SHORT)
                .show();
        System.out.println(storyId);
        System.out.println(mStory.getmUuid());*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mTitleField = (TextView) v.findViewById(R.id.story_name);
        mDetailField = (TextView) v.findViewById(R.id.story_title_description);
        mTitleField.setText(mStory.getmName());
        mDetailField.setText(mStory.getmDescription());

        mImageField = (ImageView) v.findViewById(R.id.story_image_view);
        mImageField.setImageDrawable(mStory.getmImage().getDrawable());

        return v;
    }
}