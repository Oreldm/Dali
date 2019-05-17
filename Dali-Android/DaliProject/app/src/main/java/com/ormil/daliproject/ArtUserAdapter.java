package com.ormil.daliproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtUserAdapter extends BaseAdapter {

    private static final String TAG = "ArtUserAdapter";

    private ArrayList<ArtUserModel> artUserModels;
    private Context context;

    public ArtUserAdapter(Context context, ArrayList<ArtUserModel> artUserModels) {
        this.context = context;
        this.artUserModels = artUserModels;
    }

    @Override
    public int getCount() {
        return artUserModels.size();
    }

    @Override
    public Object getItem(int i) {
        return artUserModels.get(i);
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

        ArtUserModel tempUserModel = artUserModels.get(i);

        if(tempUserModel.getProfileUrl() != "" && viewHolder.profileIcon != null)
            Picasso.get().load(tempUserModel.getProfileUrl()).into(viewHolder.profileIcon);
        if(viewHolder.profileIcon == null)
            Log.e(TAG, "Icon is null");
        viewHolder.mainText.setText(tempUserModel.getMainText());
        viewHolder.subText.setText(tempUserModel.getSubText());
        viewHolder.cornerInfo.setText(tempUserModel.getCornerInfo());

        return convertView;
    }
}
