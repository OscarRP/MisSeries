package com.example.oscarruiz.misseries.models;

import android.media.Image;

import java.util.Date;

/**
 * Created by Oscar Ruiz on 29/06/2017.
 */

public class Serie {

    /**
     * serie poster url
     */
    private String posterURL;

    /**
     * the movie db serie id
     */
    private int id;

    /**
     * var that indicates the name of the serie
     */
    private String title;

    /**
     * var that inidcates actual season user is seen or last season seen
     */
    private int season;

    /**
     * var that indicates last chapter seen
     */
    private int chapter;

    /**
     * var that indicates the day user has seen the last chapter
     */
    private Date day;

    /**
     * var that indicates the date estimated next season premiere
     */
    private String newSeason;


    public Serie(String name) {
        this.title = name;
    }

    public Serie(String title, int season) {
        this.title = title;
        this.season = season;
    }


    public Serie(String title, int season, int chapter) {
        this.title = title;
        this.season = season;
        this.chapter = chapter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getNewSeason() {
        return newSeason;
    }

    public void setNewSeason(String newSeason) {
        this.newSeason = newSeason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
}
