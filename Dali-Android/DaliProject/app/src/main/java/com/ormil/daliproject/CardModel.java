package com.ormil.daliproject;

import java.util.Date;

public class CardModel {
    private int profileImage;
    private String artworkName;
    private String artworkInfo;
    private Date creationDate;
    private boolean isLiked;

    public CardModel(int profileImage, String artworkName) {
        this.profileImage = profileImage;
        this.artworkName = artworkName;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int image) {
        this.profileImage = image;
    }

    public String getArtworkName() {
        return artworkName;
    }

    public void setArtworkName(String title) {
        this.artworkName = title;
    }
}
