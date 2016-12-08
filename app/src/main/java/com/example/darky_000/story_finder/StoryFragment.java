package com.example.darky_000.story_finder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by darky_000 on 11/20/2016.
 */

public class StoryFragment extends Fragment {
    private StoryEvent mStory;
    private TextView mTitleField;
    private TextView mDetailField;
    private ImageView mImageField;
    private TextView mIdField;
    private Button viewButton;
    private String meetUpURL;


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

        meetUpURL = mStory.getLink();

        mTitleField = (TextView) v.findViewById(R.id.story_name);
        mDetailField = (TextView) v.findViewById(R.id.story_title_description);
        mIdField = (TextView) v.findViewById(R.id.story_id);

        mIdField.setText(mStory.getId());
        mTitleField.setText(mStory.getName());
        mDetailField.setText(mStory.getDescription());


        viewButton = (Button) v.findViewById(R.id.view_story_button);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(meetUpURL);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webIntent);
            }
        });

//        mImageField = (ImageView) v.findViewById(R.id.story_image_view);
//        mImageField.setImageDrawable(mStory.getmImage().getDrawable());

        return v;
    }
}