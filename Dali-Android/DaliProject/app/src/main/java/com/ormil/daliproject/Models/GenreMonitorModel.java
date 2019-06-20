package com.ormil.daliproject.Models;

public class GenreMonitorModel {
    private int artworkId;
    private String genre;
    private float time;
    private long score;

    public GenreMonitorModel(int artworkId, String genre) {
        this.artworkId = artworkId;
        this.genre = genre;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
        calcScore();
    }

    private void calcScore() {
        for(int i = 0; i < time; i++) {
            this.score += i;
        }
    }

    @Override
    public String toString() {
        return "GenreMonitorModel{" +
                "artworkId=" + artworkId +
                ", genre='" + genre + '\'' +
                ", time=" + time +
                ", score=" + score +
                '}';
    }
}
