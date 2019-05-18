package com.ormil.daliproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ormil.daliproject.Adapters.ArtUserAdapter;
import com.ormil.daliproject.Models.ArtUserModel;
import com.ormil.daliproject.R;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    private static final String TAG = "NotificationsActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ListView mListView = findViewById(R.id.notification_list);

        /*ArrayList<NotificationModel> notificationModels = new ArrayList<>();

        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091984023L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091564000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091504000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091460000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091348000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091258000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091984023L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091564000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091504000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091460000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091348000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091258000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091984023L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091564000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091504000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091460000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091348000L)));
        notificationModels.add(new NotificationModel("", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new Date(1558091258000L)));

        NotificationAdapter notificationAdapter = new NotificationAdapter(this, notificationModels);

        mListView.setAdapter(notificationAdapter);*/

        ArrayList<ArtUserModel> artUserModels = new ArrayList<>();

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

        ArtUserAdapter artUserAdapter = new ArtUserAdapter(this, artUserModels);

        mListView.setAdapter(artUserAdapter);

    }
}
