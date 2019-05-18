package com.ormil.daliproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ArtUserModel implements Parcelable {
    private String profileUrl;
    private String mainText;
    private String subText;
    private String cornerInfo;

    public ArtUserModel() {
    }

    public ArtUserModel(@NonNull String profileUrl, String mainText, String subText, String cornerInfo) {
        this.profileUrl = profileUrl;
        this.mainText = mainText;
        this.subText = subText;
        this.cornerInfo = cornerInfo;
    }

    protected ArtUserModel(Parcel in) {
        profileUrl = in.readString();
        mainText = in.readString();
        subText = in.readString();
        cornerInfo = in.readString();
    }

    public static final Creator<ArtUserModel> CREATOR = new Creator<ArtUserModel>() {
        @Override
        public ArtUserModel createFromParcel(Parcel in) {
            return new ArtUserModel(in);
        }

        @Override
        public ArtUserModel[] newArray(int size) {
            return new ArtUserModel[size];
        }
    };

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getCornerInfo() {
        return cornerInfo;
    }

    public void setCornerInfo(String cornerInfo) {
        this.cornerInfo = cornerInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.profileUrl);
        parcel.writeString(this.mainText);
        parcel.writeString(this.subText);
        parcel.writeString(this.cornerInfo);
    }
}
