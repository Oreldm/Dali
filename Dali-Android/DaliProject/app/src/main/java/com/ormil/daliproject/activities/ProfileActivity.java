package com.ormil.daliproject.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Models.ArtUserModel;
import com.ormil.daliproject.Fragments.ListTabFragment;
import com.ormil.daliproject.Fragments.MapTabFragment;
import com.ormil.daliproject.Adapters.ProfileTabAdapter;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.Models.UserProfileModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;
import com.ormil.daliproject.Services.HttpService;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    /**
     * TODO: increment ExitService.TaskCompletedGlobal if follow/unfollow is pressed :)
     */

    private static final String TAG = "ProfileActivity";
    public static final String LIST_DATASET_KEY = "list_Dataset";
    public static final String LIST_TYPE_KEY = "list_Type";
    public static final String PROFILE_TYPE_KEY = "profile_Type";

    public enum ProfileType {
        USER_PROFILE, OTHER_PROFILE
    }

    private ProfileType profileType;

    public static final int ACTIVITY_NUMBER=4;
    private ProfileTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private UserProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        profileType = (ProfileType) getIntent().getExtras().getSerializable(PROFILE_TYPE_KEY);

        setContentView(R.layout.activity_profile);

        try {
            String profileJson = HttpService.get(HttpService.endPoint + HttpService.userPath + "/getProfileById" + "?id=" + "2");
            Gson g = new Gson();
            profileModel = g.fromJson(profileJson, UserProfileModel.class);

        }
        catch (Exception e) {
            Log.e(TAG, "Error while looking for artworks");
        }

        viewPager = findViewById(R.id.profile_viewpager);
        tabLayout = findViewById(R.id.profile_tabs);

        tabAdapter = new ProfileTabAdapter(getSupportFragmentManager());

        switch(profileType) {
            case USER_PROFILE:
                ListTabFragment likedArtworkTabFragment = new ListTabFragment();
                //ListTabFragment notificationTabFragment = new ListTabFragment();

                Bundle likeBundle = new Bundle();
                //Bundle notificationBundle = new Bundle();

                likeBundle.putParcelableArrayList(LIST_DATASET_KEY, profileModel.getLikedArtwork());
                likeBundle.putSerializable(LIST_TYPE_KEY, ListTabFragment.TabType.USER_LIKES);
                likedArtworkTabFragment.setArguments(likeBundle);

                /*notificationBundle.putParcelableArrayList();
                notificationTabFragment.setArguments(notificationBundle);*/

                tabAdapter.addFragment(likedArtworkTabFragment, "Likes");
                //tabAdapter.addFragment(notificationTabFragment, "Notifications");
                break;
            case OTHER_PROFILE:
                ListTabFragment artworksTabFragment = new ListTabFragment();
                MapTabFragment mapTabFragment = new MapTabFragment();

                Bundle artworksBundle = new Bundle();

                artworksBundle.putParcelableArrayList(LIST_DATASET_KEY, profileModel.getLikedArtwork());

                artworksTabFragment.setArguments(artworksBundle);
                mapTabFragment.setArguments(artworksBundle);

                tabAdapter.addFragment(artworksTabFragment, "Artworks");
                tabAdapter.addFragment(mapTabFragment, "Map");
                break;
        }
        /*ListTabFragment listTabFragment = new ListTabFragment();
        MapTabFragment mapTabFragment = new MapTabFragment();

        Bundle artworksBundle = new Bundle();
        artworksBundle.putParcelableArrayList(LIST_DATASET_KEY, profileModel.getArtworks());

        listTabFragment.setArguments(artworksBundle);
        mapTabFragment.setArguments(artworksBundle);

        tabAdapter.addFragment(listTabFragment, "Likes");
        tabAdapter.addFragment(mapTabFragment, "Map");*/

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
