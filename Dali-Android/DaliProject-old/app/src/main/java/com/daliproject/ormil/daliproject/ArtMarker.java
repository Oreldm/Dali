package com.daliproject.ormil.daliproject;

import java.util.Objects;

public class ArtMarker {

    String ARTID;
    String URL;
    String ARTISTID;
    String TAGS;
    float XPOSITION;
    float YPOSITION;

    public String getARTID() {
        return ARTID;
    }

    public String getURL() {
        return URL;
    }

    public String getARTISTID() {
        return ARTISTID;
    }

    public String getTAGS() {
        return TAGS;
    }

    public float getXPOSITION() {
        return XPOSITION;
    }

    public float getYPOSITION() {
        return YPOSITION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ArtMarker artMarker = (ArtMarker) o;
        return Objects.equals(URL, artMarker.URL);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URL);
    }

}
