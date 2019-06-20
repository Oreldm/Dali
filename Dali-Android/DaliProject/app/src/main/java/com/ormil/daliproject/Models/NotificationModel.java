package com.ormil.daliproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationModel extends ListModel implements Parcelable {
    //private String imageSrc;
    private String notificationInfo;
    //private Date notificationTimestamp;

    protected NotificationModel(Parcel in) {
        super(in);
        //imageSrc = in.readString();
        notificationInfo = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

    /*public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }*/

    public String getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(String notificationInfo) {
        this.notificationInfo = notificationInfo;
    }

    /*public Date getNotificationTimestamp() {
        return notificationTimestamp;
    }

    public void setNotificationTimestamp(Date notificationTimestamp) {
        this.notificationTimestamp = notificationTimestamp;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeString(imageSrc);
        parcel.writeString(notificationInfo);
    }
}
