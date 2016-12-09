package com.example.darky_000;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.darky_000.R;
import com.example.darky_000.app.App;
import com.example.darky_000.volley.VolleySingleton;

import java.util.UUID;

/**
 * Created by darky_000 on 11/20/2016.
 */

public class StoryFragment extends Fragment {
    private StoryEvent mStory;
    private TextView mTitleField;
    private TextView mDetailField;
    private TextView mRSVPLimit;
    private TextView mID;
    private TextView mLink;
    private NetworkImageView searchImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID storyId = (UUID) getActivity().getIntent()
                .getSerializableExtra(StoryActivity.EXTRA_STORY_ID);

        mStory = StoryEventList.getInstance().getStoryEvent(storyId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mTitleField = (TextView) v.findViewById(R.id.story_name);
        mDetailField = (TextView) v.findViewById(R.id.story_title_description);
        mID = (TextView) v.findViewById(R.id.story_id);
        mLink = (TextView) v.findViewById(R.id.story_link);
        mRSVPLimit = (TextView) v.findViewById(R.id.story_rsvp_limit);
        searchImage = (NetworkImageView) v.findViewById(R.id.nivImage);

        mTitleField.setText(mStory.getName());
        mDetailField.setText(mStory.getDescription());
        mID.setText("Meet Up Id:" + mStory.getId());
        mLink.setText(mStory.getLink());
        mRSVPLimit.setText(mStory.getRsvp_limit());

        ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        /**
         * Display image on top card
         */
        if(mStory.getUrlImage() != null) {
            searchImage.setImageUrl(mStory.getUrlImage(), imageLoader);
        }
        //display default image if no network image is available
        searchImage.setDefaultImageResId(R.mipmap.ic_launcher);

        mLink.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens the meetup link in web browser
             */
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(mStory.getLink());
                Intent webItent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webItent);

            }
        });
        return v;
    }
}