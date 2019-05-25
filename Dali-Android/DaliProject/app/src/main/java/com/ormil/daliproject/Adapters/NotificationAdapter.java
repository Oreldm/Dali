package com.ormil.daliproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ormil.daliproject.Models.ListModel;
import com.ormil.daliproject.Models.NotificationModel;
import com.ormil.daliproject.R;

import java.util.ArrayList;
import java.util.Date;

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListModel> dataSetList;

    public NotificationAdapter(Context context, ArrayList<ListModel> dataSetList) {
        this.context = context;
        this.dataSetList = dataSetList;
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

        NotificationViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.notification_item, viewGroup, false);
            viewHolder = new NotificationViewHolder();
            viewHolder.notificationIcon = convertView.findViewById(R.id.notification_icon);
            viewHolder.notificationInfo = convertView.findViewById(R.id.notification_info_text);
            viewHolder.notificationTimestamp = convertView.findViewById(R.id.notification_timestamp);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (NotificationViewHolder) convertView.getTag();
        }

        NotificationModel notificationModel = (NotificationModel) dataSetList.get(i);

        viewHolder.notificationInfo.setText(notificationModel.getNotificationInfo());

        Date date = new Date();
        long mins = (date.getTime() - notificationModel.getNotificationTimestamp().getTime()) / 1000 / 60;

        viewHolder.notificationTimestamp.setText( mins + " minutes ago");

        return convertView;
    }
}
