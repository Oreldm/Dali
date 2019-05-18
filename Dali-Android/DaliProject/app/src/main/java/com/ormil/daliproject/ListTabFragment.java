package com.ormil.daliproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListTabFragment extends Fragment {

    private static final String TAG = "ListTabFragment";

    enum TabType {ARTIST_ARTWORK, USER_LIKES, USER_RECOMMENDS}

    TabType fragmentListType;

    private ArrayList<ArtUserModel> dataArray;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        Log.e(TAG, "setArguments() called with: args = [" + args + "]");

        dataArray = args.getParcelableArrayList("dataList");

        if(dataArray != null) {
            Log.d(TAG, Arrays.toString(dataArray.toArray()));
        }
        else {
            Log.e(TAG, "Array is null");
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

        if(dataArray != null) {
            ArtUserAdapter artUserAdapter = new ArtUserAdapter(view.getContext(), dataArray);
            mListView.setAdapter(artUserAdapter);
        }
        else {
            Log.e(TAG, "dataArray is null");
        }
    }
}
