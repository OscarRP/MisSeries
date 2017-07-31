package com.example.oscarruiz.misseries.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Oscar Ruiz on 07/07/2017.
 */

public class Genres implements Serializable {

    /**
     * genre serie id
     */
    @SerializedName("id")
    private int genreID;

    /**
     * genre serie
     */
    @SerializedName("name")
    private String genre;

    public Genres() {
    }

    public Genres(int genreID, String genre) {
        this.genreID = genreID;
        this.genre = genre;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
