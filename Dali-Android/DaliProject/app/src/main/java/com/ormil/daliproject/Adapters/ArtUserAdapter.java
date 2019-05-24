package com.ormil.daliproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ormil.daliproject.Models.ArtUserModel;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtUserAdapter extends BaseAdapter {

    private static final String TAG = "ArtUserAdapter";

    private ArrayList<ArtworkModel> artworkModels;
    private Context context;

    public ArtUserAdapter(Context context, ArrayList<ArtworkModel> artworkModels) {
        this.context = context;
        this.artworkModels = artworkModels;
    }

    @Override
    public int getCount() {
        return artworkModels.size();
    }

    @Override
    public Object getItem(int i) {
        return artworkModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ArtUserViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.art_user_item, viewGroup, false);
            viewHolder = new ArtUserViewHolder();
            viewHolder.profileIcon = convertView.findViewById(R.id.user_item_image);
            viewHolder.mainText = convertView.findViewById(R.id.item_main_text);
            viewHolder.subText = convertView.findViewById(R.id.item_sub_text);
            viewHolder.cornerInfo = convertView.findViewById(R.id.item_corner);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ArtUserViewHolder) convertView.getTag();
        }

        ArtworkModel tempArtworkModel = artworkModels.get(i);

        if(tempArtworkModel.getArtistPicture() != null && viewHolder.profileIcon != null)
            Picasso.get().load(tempArtworkModel.getArtistPicture()).into(viewHolder.profileIcon);
        if(viewHolder.profileIcon == null)
            Log.e(TAG, "Icon is null");
        viewHolder.mainText.setText(tempArtworkModel.getName());
        viewHolder.subText.setText(tempArtworkModel.getArtistName());
        viewHolder.cornerInfo.setText(tempArtworkModel.getDt_created());

        return convertView;
    }
}
