package com.project_dali.Objects;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Artwork {
    public static List<Artwork>artworks =new ArrayList<>();

    private LatLng position;
    private String url;
    private List<String> generes;
    private String name;

    public LatLng getPosition() {
        return position;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getGeneres() {
        return generes;
    }

    public String getName(){
        return name;
    }
}
