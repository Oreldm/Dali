package com.ormil.daliproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;

public class SearchActivity extends AppCompatActivity {
    public static final int ACTIVITY_NUMBER=5;
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);
    }
}
