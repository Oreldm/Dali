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
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.HttpService;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

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
        View itemView = layoutInflater.inflate(R.layout.artwork_item, container, false);
        itemView.setTag("Tag" + position);

        ImageView profileImage;
        ImageButton likeButton, infoButton, closeInfo;
        TextView artworkName, infoTabArtwokName, infoTabArtistName, infoTabBio;

        LinearLayout parentLayout = itemView.findViewById(R.id.tabs_parent);
        RelativeLayout brief = itemView.findViewById(R.id.brief_card);
        RelativeLayout infoTab = itemView.findViewById(R.id.info_tab);

        profileImage = itemView.findViewById(R.id.item_profile_picture);
        artworkName = itemView.findViewById(R.id.artwork_name);
        likeButton = itemView.findViewById(R.id.like_button);
        infoButton = itemView.findViewById(R.id.info_button);
        closeInfo = itemView.findViewById(R.id.close_info);

        infoTabArtwokName = itemView.findViewById(R.id.info_tab_artwork_name);
        infoTabArtistName = itemView.findViewById(R.id.info_tab_artist_name);
        infoTabBio = itemView.findViewById(R.id.info_tab_bio);

        infoButton.setOnClickListener(view -> showArtworkInfo(parentLayout ,brief, infoTab));

        closeInfo.setOnClickListener(view -> closeArtworkInfo(parentLayout, brief, infoTab));

        likeButton.setOnClickListener(view -> likeArtwork(position));

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

        container.addView(itemView, 0);

        return itemView;
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
        ArtworkModel artworkModel = artworkModels.get(position);
        try {
            HttpService.likeArtwork(artworkModel.getId());
        } catch (Exception e) {
            Log.e(TAG, "likeArtwork: Error while liking art.");
        }
        Log.e("LIKE", " " + position);
        notifyDataSetChanged();
    }

    private void showArtworkInfo(LinearLayout parentLayout, RelativeLayout brief, RelativeLayout infoTab){
        brief.setVisibility(View.GONE);
        infoTab.setVisibility(View.VISIBLE);
        parentLayout.invalidate();
    }

    private void closeArtworkInfo(LinearLayout parentLayout, RelativeLayout brief, RelativeLayout infoTab) {
        brief.setVisibility(View.VISIBLE);
        infoTab.setVisibility(View.GONE);
        parentLayout.invalidate();
    }

}

