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
    public static final String ARTLIST_KEY = "artwork_List";

    public static final int ACTIVITY_NUMBER=4;
    private ProfileTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ArrayList<ArtUserModel> artUserModels = new ArrayList<>();

    private UserProfileModel profileModel;
    private ArrayList<ArtworkModel> artworkModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        setContentView(R.layout.activity_profile);

        try {
            String profileJson = HttpService.get(HttpService.endPoint + HttpService.userPath + "/getProfileById" + "?id=" + "3");
            Gson g = new Gson();
            profileModel = g.fromJson(profileJson, UserProfileModel.class);

        /*Type listType = new TypeToken<ArrayList<ArtworkModel>>() {}.getType();
            artworkModels = g.fromJson(artworksJson,  listType);*/
            Log.d(TAG, "*******************************************************************************************************");
            Log.d(TAG, "*******************************************************************************************************");
            Log.d(TAG, "artworkList: " + profileModel.toString());
            Log.d(TAG, "*******************************************************************************************************");
            Log.d(TAG, "*******************************************************************************************************");

        }
        catch (Exception e) {
            Log.e(TAG, "Error while looking for artworks");
        }

        viewPager = findViewById(R.id.profile_viewpager);
        tabLayout = findViewById(R.id.profile_tabs);

        tabAdapter = new ProfileTabAdapter(getSupportFragmentManager());

        ListTabFragment listTabFragment = new ListTabFragment();
        MapTabFragment mapTabFragment = new MapTabFragment();

        setArray();

        Bundle artworksBundle = new Bundle();
        artworksBundle.putParcelableArrayList(ARTLIST_KEY, profileModel.getArtworks());
        //artworksBundle.putParcelableArrayList("dataList", artUserModels);

        listTabFragment.setArguments(artworksBundle);
        mapTabFragment.setArguments(artworksBundle);

        tabAdapter.addFragment(listTabFragment, "Likes");
        tabAdapter.addFragment(mapTabFragment, "Map");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setArray() {
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
        artUserModels.add(new ArtUserModel("https://avatars1.githubusercontent.com/u/9754901?s=400&v=4", "Sir Lolo", "Flat Flat", ""));
    }
}
