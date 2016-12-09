package com.example.darky_000;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.darky_000.R;
import com.example.darky_000.app.App;
import com.example.darky_000.controller.JsonController;
import com.example.darky_000.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

//import android.widget.Button;
//import android.widget.Toast;

/**
 * Created by darky_000 on 11/21/2016.
 */

public class StoryListFragment extends Fragment {
    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdapter;
    private SearchView searchView;
    private JsonController controller;
    //private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_list, container, false);

        mStoryRecyclerView = (RecyclerView) view
                .findViewById(R.id.story_recycle_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = (SearchView) view
                .findViewById(R.id.search_name);

        mAdapter = new StoryAdapter(new ArrayList<StoryEvent>());
        mStoryRecyclerView.setAdapter(mAdapter);
        
        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<StoryEvent> storyEvents) {
                        if(storyEvents.size() > 0) {
                            mAdapter.updateList(storyEvents);

                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("onFailure",errorMessage);
                        //textView.setVisibility(View.VISIBLE);
                        //textView.setText("Failed to retrieve data");
                    }
                }
        );


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if there is text
                if (newText.length() > 1){
                    mStoryRecyclerView.setVisibility(View.VISIBLE);
                    controller.cancelAllRequests();
                    controller.sendRequest(newText);
                } else if(newText.equals("")){
                    mStoryRecyclerView.setVisibility(View.GONE);
                }
                return false;
            }
        });
        searchView.setIconified(false);


        return view;
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

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_story_name_text_view);
            //mDescriptionTV = (TextView) itemView.findViewById(R.id.list_item_story_description_text_view);
            searchImage = (NetworkImageView) itemView.findViewById(R.id.list_item_story_image);

        }

        public void bindStory(StoryEvent storyEvent) {
            mStory = storyEvent;
            mNameTextView.setText(mStory.getName());

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