package com.ormil.daliproject.Models;

public class GenreMonitorModel {
    private int artworkId;
    private int genreId;
    private float time;
    private long score;

    public GenreMonitorModel(int artworkId, int genreId) {
        this.artworkId = artworkId;
        this.genreId = genreId;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenre(String genre) {
        this.genreId = genreId;
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
                ", genre='" + genreId + '\'' +
                ", time=" + time +
                ", score=" + score +
                '}';
    }
}
