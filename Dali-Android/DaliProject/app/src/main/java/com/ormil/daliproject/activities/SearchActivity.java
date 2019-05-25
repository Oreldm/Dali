package com.ormil.daliproject.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ormil.daliproject.Adapters.ArtUserAdapter;
import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.Models.ListModel;
import com.ormil.daliproject.Models.UserProfileModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;
import com.ormil.daliproject.Services.HttpService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
    public static final int ACTIVITY_NUMBER=5;
    private static final String TAG = "SearchActivity";

    public static Context context;

    private ArrayList<ListModel> userProfileModels = new ArrayList<>();
    private ArtUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        context=this;
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        EditText editText = findViewById(R.id.search_edit_text);
        ListView listView = findViewById(R.id.search_list);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Timer T=new Timer();
                    T.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            try {
                                                Log.d(TAG, "run() called");
                                                String profileJson = HttpService.get(HttpService.endPoint + HttpService.userPath + "/search" + "?str=" + charSequence);
                                                Gson g = new Gson();
                                                Type listType = new TypeToken<ArrayList<UserProfileModel>>() {
                                                }.getType();
                                                userProfileModels = g.fromJson(profileJson, listType);
                                                adapter.updateList(userProfileModels);
                                                /*adapter = new ArtUserAdapter(SearchActivity.context, userProfileModels, ArtUserAdapter.AdapterType.USER_FOCUS);
                                                listView.setAdapter(adapter);*/
                                            } catch (Exception e) {
                                                Log.e(TAG, "Error while looking for artworks");
                                            }
                                        }
                                    });
                                }
                            },1000

                    );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            onListItemClick(i);
        });

        adapter = new ArtUserAdapter(this, userProfileModels, ArtUserAdapter.AdapterType.USER_FOCUS);
        listView.setAdapter(adapter);
    }

    private void onListItemClick(int position) {
        Intent intent = new Intent(this, ProfileActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(ProfileActivity.PROFILE_TYPE_KEY, ProfileActivity.ProfileType.OTHER_PROFILE);
        bundle.putParcelable(ProfileActivity.PROFILE_USER_DATA, userProfileModels.get(position));

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
