package com.ormil.daliproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserProfileModel extends ListModel implements Parcelable {
    private String id;
    private String pictureUrl = null;
    private String name;
    ArrayList<String> generes = new ArrayList<>();
    private String bio = null;
    ArrayList<ArtworkModel> artworks = new ArrayList<>();
    ArrayList<ArtworkModel> likedArtwork = new ArrayList<>();
    /*ArrayList<UserProfileModel> following = new ArrayList<>();
    ArrayList<UserProfileModel> recommendedGeneres = new ArrayList<>();
    ArrayList<UserProfileModel> recommendedArtists = new ArrayList<>();
    ArrayList<UserProfileModel> followers = new ArrayList<>();*/

    protected UserProfileModel(Parcel in) {
        super(in);
        id = in.readString();
        pictureUrl = in.readString();
        name = in.readString();
        generes = in.createStringArrayList();
        bio = in.readString();
        artworks = in.createTypedArrayList(ArtworkModel.CREATOR);
        likedArtwork = in.createTypedArrayList(ArtworkModel.CREATOR);
    }

    public static final Creator<UserProfileModel> CREATOR = new Creator<UserProfileModel>() {
        @Override
        public UserProfileModel createFromParcel(Parcel in) {
            return new UserProfileModel(in);
        }

        @Override
        public UserProfileModel[] newArray(int size) {
            return new UserProfileModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getGeneres() {
        return generes;
    }

    public void setGeneres(ArrayList<String> generes) {
        this.generes = generes;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<ArtworkModel> getArtworks() {
        return artworks;
    }

    public void setArtworks(ArrayList<ArtworkModel> artworks) {
        this.artworks = artworks;
    }

    public ArrayList<ArtworkModel> getLikedArtwork() {
        return likedArtwork;
    }

    public void setLikedArtwork(ArrayList<ArtworkModel> likedArtwork) {
        this.likedArtwork = likedArtwork;
    }

    @Override
    public String toString() {
        return "UserProfileModel{" +
                "id=" + id +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", name='" + name + '\'' +
                ", generes=" + generes +
                ", bio='" + bio + '\'' +
                ", artworks=" + artworks +
                ", likedArtwork=" + likedArtwork +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(pictureUrl);
        parcel.writeString(name);
        parcel.writeStringList(generes);
        parcel.writeString(bio);
        parcel.writeTypedList(artworks);
        parcel.writeTypedList(likedArtwork);
    }
}
