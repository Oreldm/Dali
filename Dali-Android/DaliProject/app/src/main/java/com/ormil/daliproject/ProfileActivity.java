package com.ormil.daliproject;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    private ProfileTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        viewPager = findViewById(R.id.profile_viewpager);
        tabLayout = findViewById(R.id.profile_tabs);

        tabAdapter = new ProfileTabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new LikeTabFragment(), "Likes");
        tabAdapter.addFragment(new LikeTabFragment(), "Recommended");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
