package com.example.oscarruiz.misseries.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Oscar Ruiz on 07/07/2017.
 */

public class ResponseSerie implements Serializable {

    /**
     * The movie db serie id
     */
    @SerializedName("id")
    private int id;

    /**
     * IMDb serie id
     */
    @SerializedName("imdb_id")
    private String imdbID;

    /**
     * Serie status if its ended
     */
    @SerializedName("status")
    private String status;

    /**
     * Serie name
     */
    @SerializedName("name")
    private String name;

    /**
     * the movie db poster url
     */
    @SerializedName("poster_path")
    private String poster;

    /**
     * serie description
     */
    @SerializedName("overview")
    private String overview;

    /**
     * Runtime episode
     */
    @SerializedName("episode_run_time")
    private ArrayList<Integer> episodeRunTime;

    /**
     * first air date
     */
    @SerializedName("first_air_date")
    private String airDate;

    /**
     * Serie genres
     */
    @SerializedName("genres")
    private ArrayList<Genres> genres;

    /**
     * Serie seasons
     */
    @SerializedName("seasons")
    private ArrayList<Season> seasons;

    /**
     * Vote Average
     */
    @SerializedName("vote_average")
    private Double voteAverage;

    public ResponseSerie() {
    }

    public ResponseSerie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(ArrayList<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
