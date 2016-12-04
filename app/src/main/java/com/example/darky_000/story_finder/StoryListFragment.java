package com.example.darky_000.story_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
//import android.widget.Toast;
import java.util.List;

/**
 * Created by darky_000 on 11/21/2016.
 */

public class StoryListFragment extends Fragment {
    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdapter;
    private SearchView searchView;
    //private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_list, container, false);

        mStoryRecyclerView = (RecyclerView) view
                .findViewById(R.id.story_recycle_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = (SearchView) view
                .findViewById(R.id.search_name);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateUI(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateUI(newText);
                return false;
            }
        });
        searchView.setIconified(false);
        /*searchButton = (Button) view
                .findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                updateUI(String.valueOf(searchView.getQuery()));
                Toast.makeText(getContext(), "Our Word : " + String.valueOf(searchView.getQuery()),
                        Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
            }
        });*/
        //updateUI("");

        return view;
    }


    //Added on 11/21
    @Override
    public void onResume() {
        super.onResume();
        searchView.clearFocus();
        //updateUI();
    }

    private void updateUI(String query) {
        StoryLab storyLab = StoryLab.get(getActivity());
        List<Story> stories = storyLab.getStories(query);
        mAdapter = new StoryAdapter(stories);
        mStoryRecyclerView.setAdapter(mAdapter);
    }

    private class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mDescriptionTV;
        private ImageView mImageView;
        private Story mStory;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_story_name_text_view);
            mDescriptionTV = (TextView) itemView.findViewById(R.id.list_item_story_description_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.list_item_story_image);
        }

        public void bindStory(Story story) {
            mStory = story;
            mNameTextView.setText(mStory.getmName());
            mDescriptionTV.setText(mStory.getmDescription());
            mImageView.setImageDrawable(mStory.getmImage().getDrawable());

        }

        @Override
        public void onClick(View v) {
            /*Toast.makeText(getActivity(),
                    mStory.getmUuid() + "clicked!", Toast.LENGTH_SHORT)
                    .show();*/
            //Intent intent = new Intent(getActivity(), StoryActivity.class);
            Intent intent = StoryActivity.newIntent(getActivity(), mStory.getmUuid());
            startActivity(intent);
        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> {
        private List<Story> mStories;

        public StoryAdapter(List<Story> stories) {
            mStories = stories;
        }

        @Override
        public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_story, parent, false);
            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(StoryHolder holder, int position) {
            Story story = mStories.get(position);
            holder.bindStory(story);
        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }

    }
}