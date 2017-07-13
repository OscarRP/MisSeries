package com.example.oscarruiz.misseries.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Carlos Ruiz on 07/07/2017.
 */

public class Season implements Serializable {

    /**
     * Season air date
     */
    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episode_count")
    private int episodeCount;

    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String seasonPoster;

    @SerializedName("season_number")
    private int seasonNumber;

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
}
