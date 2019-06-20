package com.ormil.daliproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.Models.ListModel;
import com.ormil.daliproject.Models.UserProfileModel;
import com.ormil.daliproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtUserAdapter extends BaseAdapter {

    private static final String TAG = "ArtUserAdapter";

    public enum AdapterType {
        ARTWORK_FOCUS, USER_FOCUS
    }

    private AdapterType adapterType;

    private ArrayList<ListModel> dataSetList;
    private Context context;


    public ArtUserAdapter(Context context, ArrayList<ListModel> dataSetList, AdapterType adapterType) {
        this.context = context;
        this.dataSetList = dataSetList;
        this.adapterType = adapterType;
    }

    @Override
    public int getCount() {
        return dataSetList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSetList.get(i);
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

        switch(adapterType) {
            case ARTWORK_FOCUS:
                ArtworkModel tempArtworkModel = (ArtworkModel) dataSetList.get(i);

                if(tempArtworkModel.getArtistPicture() != null && viewHolder.profileIcon != null)
                    Picasso.get().load(tempArtworkModel.getArtistPicture()).into(viewHolder.profileIcon);
                if(viewHolder.profileIcon == null)
                    Log.e(TAG, "Icon is null");
                viewHolder.mainText.setText(tempArtworkModel.getName());
                viewHolder.subText.setText(tempArtworkModel.getArtistName());
                viewHolder.cornerInfo.setText(tempArtworkModel.getGeneres().get(0));
                break;
            case USER_FOCUS:
                UserProfileModel userProfileModel = (UserProfileModel) dataSetList.get(i);

                if(userProfileModel.getPictureUrl() != null && viewHolder.profileIcon != null)
                    Picasso.get().load(userProfileModel.getPictureUrl()).into(viewHolder.profileIcon);
                if(viewHolder.profileIcon == null)
                    Log.e(TAG, "Icon is null");
                viewHolder.mainText.setText(userProfileModel.getName());
                try {
                    viewHolder.subText.setText(userProfileModel.getGeneres().get(0));
                } catch (IndexOutOfBoundsException e) {
                    Log.e(TAG, "No generes found!");
                }
                viewHolder.cornerInfo.setText("");
                break;
        }

        return convertView;
    }

    public void updateList(ArrayList<ListModel> dataSetList) {
        this.dataSetList = dataSetList;
        notifyDataSetChanged();
    }
}
