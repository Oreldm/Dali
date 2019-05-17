package com.ormil.daliproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class LikeTabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.like_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView mListView = view.findViewById(R.id.tab_list);

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

        ArtUserAdapter artUserAdapter = new ArtUserAdapter(view.getContext(), artUserModels);

        mListView.setAdapter(artUserAdapter);
    }
}
