package com.ormil.daliproject.Models;

import java.util.ArrayList;

public class UserProfileModel {
    private float id;
    private String pictureUrl = null;
    private String name;
    ArrayList<String> generes = new ArrayList<>();
    private String bio = null;
    ArrayList<ArtworkModel> artworks = new ArrayList<>();
    ArrayList<ArtworkModel> likedArtwork = new ArrayList<>();
    //ArrayList<Object> following = new ArrayList<>();
    //ArrayList<Object> recommendedGeneres = new ArrayList<>();
    //ArrayList<Object> recommendedArtists = new ArrayList<>();
    //ArrayList<Object> followers = new ArrayList<>();

    public float getId() {
        return id;
    }

    public void setId(float id) {
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
}
