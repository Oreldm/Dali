package com.ormil.daliproject.Models;

import java.util.Date;

public class CardModel {
    private int profileImage;
    private String artworkName;
    private String artistName;
    private String artworkInfo;
    private Date creationDate;
    private boolean isLiked;

    public CardModel(int profileImage, String artworkName) {
        this.profileImage = profileImage;
        this.artworkName = artworkName;
    }

    public CardModel(int profileImage, String artworkName, String artistName, String artworkInfo) {
        this.profileImage = profileImage;
        this.artworkName = artworkName;
        this.artistName = artistName;
        this.artworkInfo = artworkInfo;
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

    public void setArtworkName(String artworkName) {
        this.artworkName = artworkName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtworkInfo() {
        return artworkInfo;
    }

    public void setArtworkInfo(String artworkInfo) {
        this.artworkInfo = artworkInfo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void likeArt() {
        isLiked = !isLiked;
    }

}
