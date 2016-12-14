package com.example.darky_000;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.darky_000.app.App;
import com.example.darky_000.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darky_000 on 11/21/2016.
 */

public class StoryListFragment extends Fragment {
    StoryEvent storyEvent;
    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdapter;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_list, container, false);

        mStoryRecyclerView = (RecyclerView) view
                .findViewById(R.id.story_recycle_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = (SearchView) view
                .findViewById(R.id.search_name);

        mAdapter = new StoryAdapter(storyEvent.get().getStoryEvents(" "));
        mStoryRecyclerView.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if there is text
                updateUI(newText);
                return false;
            }
        });
        searchView.setIconified(false);


        return view;
    }

    private void updateUI(String query) {
        storyEvent = StoryEvent.get();
        List<StoryEvent> storyEvents = storyEvent.getStoryEvents(query);
        StoryAdapter mAdapter;
        mAdapter = new StoryAdapter(storyEvents);
        mStoryRecyclerView.setAdapter(mAdapter);
    }


    //Added on 11/21
    @Override
    public void onResume() {
        super.onResume();
        searchView.clearFocus();
    }


    private class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private StoryEvent mStory;
        private NetworkImageView searchImage;
        private TextView mRSVPView;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_story_name_text_view);
            searchImage = (NetworkImageView) itemView.findViewById(R.id.nivImage);
            mRSVPView = (TextView) itemView.findViewById(R.id.list_item_story_rvsp_limit);

        }

        public void bindStory(StoryEvent storyEvent) {
            mStory = storyEvent;
            mNameTextView.setText(mStory.getName());

            mRSVPView.setText("Number of Attendances: " + mStory.getRsvp_limit());

            ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();

            if(storyEvent.getUrlImage() != null) {
                this.searchImage.setImageUrl(storyEvent.getUrlImage(), imageLoader);
            }
            searchImage.setDefaultImageResId(R.drawable.noimage);


        }

        @Override
        public void onClick(View v) {
            Intent intent = StoryActivity.newIntent(getActivity(), mStory.getmUuid());
            //Save StoryList before starting new activity
            StoryEventList.getInstance().setList(mAdapter.mStories);
            startActivity(intent);
        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> {
        private List<StoryEvent> mStories;

        public StoryAdapter(List<StoryEvent> stories) {
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
            StoryEvent storyEvent = mStories.get(position);
            holder.bindStory(storyEvent);
        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }

        public void updateList(List<StoryEvent> storyEvents){
            mStories = storyEvents;
            notifyDataSetChanged();
        }


    }
}