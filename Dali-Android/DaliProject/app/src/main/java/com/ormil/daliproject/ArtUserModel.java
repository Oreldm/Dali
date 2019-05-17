package com.ormil.daliproject;

import android.support.annotation.NonNull;

public class ArtUserModel {
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
}
