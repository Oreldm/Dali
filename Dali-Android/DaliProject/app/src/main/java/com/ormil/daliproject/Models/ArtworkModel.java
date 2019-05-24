package com.ormil.daliproject.Models;

import java.util.List;

public class ArtworkModel {
    private int id;
    private String path;
    private String name;
    private int artistId;
    private double positionX;
    private double positionY;
    private String dt_created;
    private List<String> generes;
    private List<Integer> generesIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public String getDt_created() {
        return dt_created;
    }

    public void setDt_created(String dt_created) {
        this.dt_created = dt_created;
    }

    public List<String> getGeneres() {
        return generes;
    }

    public void setGeneres(List<String> generes) {
        this.generes = generes;
    }

    public List<Integer> getGeneresIds() {
        return generesIds;
    }

    public void setGeneresIds(List<Integer> generesIds) {
        this.generesIds = generesIds;
    }

    @Override
    public String toString() {
        return "ArtworkModel{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", artistId=" + artistId +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", dt_created='" + dt_created + '\'' +
                ", generes=" + generes +
                ", generesIds=" + generesIds +
                '}';
    }
}
