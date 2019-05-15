package com.ormil.daliproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends PagerAdapter {

    private List<CardModel> cardModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardAdapter(List<CardModel> cardModels, Context context) {
        this.cardModels = cardModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cardModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.artwork_item, container, false);

        ImageView imageView;
        TextView title;

        imageView = view.findViewById(R.id.item_profile_picture);
        //title = view.findViewById(R.id.item_title);

        imageView.setImageResource(cardModels.get(position).getProfileImage());
        //title.setText(cardModels.get(position).getArtworkName());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
