package com.example.darky_000;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

/**
 * Created by darky_000 on 11/21/2016.
 */

public class StoryListFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    StoryEvent storyEvent;
    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdapter;
    private SearchView searchView;
    GoogleApiClient mGoogleApiClient;


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


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(
                                        getActivity(), 1000);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    }
}