package com.example.darky_000;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.darky_000.story_finder.R;
import com.example.darky_000.story_finder.app.App;
import com.example.darky_000.story_finder.volley.VolleySingleton;

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
        mID = (TextView) v.findViewById(R.id.story_id);
        mLink = (TextView) v.findViewById(R.id.story_link);
        mRSVPLimit = (TextView) v.findViewById(R.id.story_rsvp_limit);
        searchImage = (NetworkImageView) v.findViewById(R.id.list_item_story_image);


        mTitleField.setText(mStory.getName());
        mDetailField.setText(mStory.getDescription());
        mID.setText(mStory.getId());
        mLink.setText(mStory.getLink());
        mRSVPLimit.setText(mStory.getRsvp_limit());

        ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        searchImage.setImageUrl(mStory.getUrlImage(),imageLoader);

//        mImageField = (ImageView) v.findViewById(R.id.story_image_view);
//        mImageField.setImageDrawable(mStory.getmImage().getDrawable());

        return v;
    }
}