package com.ormil.daliproject.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Fragments.ListTabFragment;
import com.ormil.daliproject.Fragments.MapTabFragment;
import com.ormil.daliproject.Adapters.ProfileTabAdapter;
import com.ormil.daliproject.Models.UserProfileModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;
import com.ormil.daliproject.Services.HttpService;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    /**
     * TODO: increment ExitService.TaskCompletedGlobal if follow/unfollow is pressed :)
     */

    private static final String TAG = "ProfileActivity";
    public static final String LIST_DATASET_KEY = "list_Dataset";
    public static final String LIST_TYPE_KEY = "list_Type";
    public static final String PROFILE_TYPE_KEY = "profile_Type";
    public static final String PROFILE_USER_ID = "user_ID";

    public enum ProfileType {
        USER_PROFILE, OTHER_PROFILE
    }

    private ProfileType profileType;

    public static final int ACTIVITY_NUMBER=4;
    /*private ProfileTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;*/

    private UserProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);

        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        Bundle bundle = getIntent().getExtras();
        profileType = (ProfileType) bundle.getSerializable(PROFILE_TYPE_KEY);

        setContentView(R.layout.activity_profile);

        TextView userName = findViewById(R.id.profile_main_text);
        TextView topName = findViewById(R.id.profile_top_bar_name);
        TextView userGenre = findViewById(R.id.profile_sub_text);
        TextView userBio = findViewById(R.id.profile_bio);
        ImageView userPicture = findViewById(R.id.profile_picture);
        ImageButton backBtn = findViewById(R.id.profile_top_bar_back_button);

        ViewPager viewPager = findViewById(R.id.profile_viewpager);
        TabLayout tabLayout = findViewById(R.id.profile_tabs);

        ProfileTabAdapter tabAdapter = new ProfileTabAdapter(getSupportFragmentManager());

        backBtn.setOnClickListener(view -> onBackPressed());

        String id = bundle.getString(PROFILE_USER_ID);
        try {
            String profileJson = HttpService.getProfileById(id);
            Gson g = new Gson();
            profileModel = g.fromJson(profileJson, UserProfileModel.class);
        }
        catch (Exception e) {
            Log.e(TAG,e.toString());
            Log.e(TAG, "Error while looking for artworks");
            e.printStackTrace();
        }

        userName.setText(profileModel.getName());
        topName.setText(profileModel.getName());
        try {
            userGenre.setText(profileModel.getGeneres().get(0));
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "Failed to retrieve text");
        }
        try {
            userBio.setText(profileModel.getBio());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to retrieve text");
        }
        Picasso.get().load(profileModel.getPictureUrl()).into(userPicture);

        switch(profileType) {
            case USER_PROFILE:

                /*ArrayList<String> followNotification = new ArrayList<>();
                ArrayList<UserProfileModel> artistRecommend = new ArrayList<>();
                try {
                    String notificationArray = HttpService.getNotificationById("2");
                    JSONArray jsonArray = new JSONArray(notificationArray);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        String temp = jsonArray.getString(i);
                        try {
                            artistRecommend.add((UserProfileModel) new Gson().fromJson(temp, UserProfileModel.class));
                        } catch (Exception e) {
                            followNotification.add(temp);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Could not fetch notifications");
                }*/

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

                artworksBundle.putParcelableArrayList(LIST_DATASET_KEY, profileModel.getArtworks());
                artworksBundle.putSerializable(LIST_TYPE_KEY, ListTabFragment.TabType.ARTIST_ARTWORK);

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
