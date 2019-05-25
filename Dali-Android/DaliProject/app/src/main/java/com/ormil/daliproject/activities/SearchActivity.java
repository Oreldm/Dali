package com.ormil.daliproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class SearchActivity extends AppCompatActivity {
    public static final int ACTIVITY_NUMBER=5;
    private static final String TAG = "SearchActivity";

    private ArrayList<ListModel> userProfileModels = new ArrayList<>();
    private ArtUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
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
                try {
                    String profileJson = HttpService.get(HttpService.endPoint + HttpService.userPath + "/search" + "?str=" + charSequence);
                    Gson g = new Gson();
                    Type listType = new TypeToken<ArrayList<UserProfileModel>>() {}.getType();
                    userProfileModels = g.fromJson(profileJson, listType);

                    adapter.updateList(userProfileModels);
                }
                catch (Exception e) {
                    Log.e(TAG, "Error while looking for artworks");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter = new ArtUserAdapter(this, userProfileModels, ArtUserAdapter.AdapterType.USER_FOCUS);
        listView.setAdapter(adapter);
    }
}
