package com.ormil.daliproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ArtworkModel implements Parcelable {
    private int id;
    private String path;
    private String name;
    private int artistId;
    private double lat;
    private double lng;
    private String dt_created;
    private List<String> generes;
    private List<Integer> generesIds;
    private String artistPicture;
    private String artistName;


    protected ArtworkModel(Parcel in) {
        id = in.readInt();
        path = in.readString();
        name = in.readString();
        artistId = in.readInt();
        lat = in.readDouble();
        lng = in.readDouble();
        dt_created = in.readString();
        generes = in.createStringArrayList();
        in.readList(generesIds, Integer.class.getClassLoader());
        artistPicture = in.readString();
        artistName = in.readString();
    }

    public static final Creator<ArtworkModel> CREATOR = new Creator<ArtworkModel>() {
        @Override
        public ArtworkModel createFromParcel(Parcel in) {
            return new ArtworkModel(in);
        }

        @Override
        public ArtworkModel[] newArray(int size) {
            return new ArtworkModel[size];
        }
    };

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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

    public String getArtistPicture() {
        return artistPicture;
    }

    public void setArtistPicture(String artistPicture) {
        this.artistPicture = artistPicture;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "ArtworkModel{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", artistId=" + artistId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", dt_created='" + dt_created + '\'' +
                ", generes=" + generes +
                ", generesIds=" + generesIds +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.path);
        parcel.writeString(this.name);
        parcel.writeInt(this.artistId);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lng);
        parcel.writeString(this.dt_created);
        parcel.writeList(this.generes);
        parcel.writeList(this.generesIds);
        parcel.writeString(this.artistPicture);
        parcel.writeString(this.artistName);
    }
}
