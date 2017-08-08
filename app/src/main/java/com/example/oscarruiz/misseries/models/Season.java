package com.example.oscarruiz.misseries.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Oscar Ruiz on 07/07/2017.
 */

public class Season implements Serializable {

    /**
     * Season air date
     */
    @SerializedName("air_date")
    private String airDate;

    /**
     * Episode count
     */
    @SerializedName("episode_count")
    private int episodeCount;

    /**
     * Season id
     */
    @SerializedName("id")
    private int id;

    /**
     * Season poster path
     */
    @SerializedName("poster_path")
    private String seasonPoster;

    /**
     * Season number
     */
    @SerializedName("season_number")
    private int seasonNumber;

    /**
     * Season overview
     */
    @SerializedName("overview")
    private String overview;


    public Season(String airDate, int episodeCount, int id, String seasonPoster, int seasonNumber) {
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.id = id;
        this.seasonPoster = seasonPoster;
        this.seasonNumber = seasonNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeasonPoster() {
        return seasonPoster;
    }

    public void setSeasonPoster(String seasonPoster) {
        this.seasonPoster = seasonPoster;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
