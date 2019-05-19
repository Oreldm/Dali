package com.ormil.daliproject.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Models.ArtUserModel;
import com.ormil.daliproject.Fragments.ListTabFragment;
import com.ormil.daliproject.Fragments.MapTabFragment;
import com.ormil.daliproject.Adapters.ProfileTabAdapter;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    /**
     * TODO: increment ExitService.TaskCompletedGlobal if follow/unfollow is pressed :)
     */
    public static final int ACTIVITY_NUMBER=4;
    private ProfileTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ArrayList<ArtUserModel> artUserModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        setContentView(R.layout.activity_profile);
        viewPager = findViewById(R.id.profile_viewpager);
        tabLayout = findViewById(R.id.profile_tabs);

        tabAdapter = new ProfileTabAdapter(getSupportFragmentManager());

        ListTabFragment listTabFragment = new ListTabFragment();

        setArray();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dataList", artUserModels);

        listTabFragment.setArguments(bundle);

        tabAdapter.addFragment(listTabFragment, "Likes");
        tabAdapter.addFragment(new MapTabFragment(), "Map");

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
