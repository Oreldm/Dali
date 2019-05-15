package com.ormil.daliproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        view.setTag("Tag" + position);

        ImageView profileImage;
        ImageButton likeButton, infoButton;
        TextView artworkName;

        LinearLayout parentLayout = view.findViewById(R.id.tabs_parent);
        RelativeLayout brief = view.findViewById(R.id.brief_card);
        RelativeLayout infoTab = view.findViewById(R.id.info_tab);

        profileImage = view.findViewById(R.id.item_profile_picture);
        artworkName = view.findViewById(R.id.artwork_name);
        likeButton = view.findViewById(R.id.like_button);
        infoButton = view.findViewById(R.id.info_button);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showArtworkInfo(parentLayout ,brief, infoTab);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeArtwork(position);
            }
        });

        profileImage.setImageResource(cardModels.get(position).getProfileImage());
        artworkName.setText(cardModels.get(position).getArtworkName());

        if(cardModels.get(position).isLiked())
            likeButton.setBackgroundResource(R.drawable.ic_like_icon_background);
        else
            likeButton.setBackground(null);

        container.addView(view, 0);

        return view;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private void likeArtwork(int position) {
        cardModels.get(position).likeArt();
        Log.e("LIKE", " " + position);
        notifyDataSetChanged();
    }

    private void showArtworkInfo(LinearLayout parentLayout, RelativeLayout brief, RelativeLayout infoTab){
        brief.setVisibility(View.GONE);
        infoTab.setVisibility(View.VISIBLE);
        parentLayout.invalidate();
    }

}

