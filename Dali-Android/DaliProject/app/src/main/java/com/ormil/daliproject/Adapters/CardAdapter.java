package com.ormil.daliproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.Models.CardModel;
import com.ormil.daliproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends PagerAdapter {

    private List<ArtworkModel> artworkModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardAdapter(List<ArtworkModel> artworkModels, Context context) {
        this.artworkModels = artworkModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return artworkModels.size();
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
        TextView artworkName, infoTabArtwokName, infoTabArtistName, infoTabBio;

        LinearLayout parentLayout = view.findViewById(R.id.tabs_parent);
        RelativeLayout brief = view.findViewById(R.id.brief_card);
        RelativeLayout infoTab = view.findViewById(R.id.info_tab);

        profileImage = view.findViewById(R.id.item_profile_picture);
        artworkName = view.findViewById(R.id.artwork_name);
        likeButton = view.findViewById(R.id.like_button);
        infoButton = view.findViewById(R.id.info_button);

        infoTabArtwokName = view.findViewById(R.id.info_tab_artwork_name);
        infoTabArtistName = view.findViewById(R.id.info_tab_artist_name);
        infoTabBio = view.findViewById(R.id.info_tab_bio);

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

        ArtworkModel artworkModel = artworkModels.get(position);

        //profileImage.setImageResource(artworkModels.get(position).getProfileImage());
        Picasso.get().load(artworkModel.getArtistPicture()).into(profileImage);
        artworkName.setText(artworkModel.getName());
        infoTabArtwokName.setText(artworkModel.getName());
        infoTabArtistName.setText(artworkModel.getArtistName());
        //infoTabBio.setText(tempAM.getArtworkInfo());

        if(/*tempAM.isLiked()*/ false)
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
        //artworkModels.get(position).likeArt();
        Log.e("LIKE", " " + position);
        notifyDataSetChanged();
    }

    private void showArtworkInfo(LinearLayout parentLayout, RelativeLayout brief, RelativeLayout infoTab){
        brief.setVisibility(View.GONE);
        infoTab.setVisibility(View.VISIBLE);
        parentLayout.invalidate();
    }

}

