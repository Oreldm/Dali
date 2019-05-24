package com.ormil.daliproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ormil.daliproject.Adapters.ArtUserAdapter;
import com.ormil.daliproject.Models.ArtUserModel;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.activities.ProfileActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ListTabFragment extends Fragment {

    private static final String TAG = "ListTabFragment";

    enum TabType {ARTIST_ARTWORK, USER_LIKES, USER_RECOMMENDS}

    TabType fragmentListType;

    private ArrayList<ArtworkModel> artworkModels;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        Log.e(TAG, "setArguments() called with: args = [" + args + "]");

        try {
            artworkModels = args.getParcelableArrayList(ProfileActivity.ARTLIST_KEY);
        } catch (NullPointerException e) {
            Toast toast = Toast.makeText(getContext(), "Error while loading art map", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflater.inflate(R.layout.list_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");

        ListView mListView = view.findViewById(R.id.tab_list);

        if(artworkModels != null) {
            ArtUserAdapter artUserAdapter = new ArtUserAdapter(view.getContext(), artworkModels);
            mListView.setAdapter(artUserAdapter);
        }
        else {
            Log.e(TAG, "dataArray is null");
        }
    }
}
