package com.example.oscarruiz.misseries.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oscar Ruiz on 05/07/2017.
 */

public class Constants {

    /**
     * Time showing splash activity
     */
    public static final int SPLASH_TIME = 4000;

    /**
     * Defines time incremental
     */
    public static final int INCREMENTAL_TIME = 1000;

    /**
     * Indicates if user has been logged in
     */
    public static final String USER_LOGGED = "user_loged";

    /**
     * Indicates users email that is logged in
     */
    public static final String UID = "uid";

    /**
     * Sorting options
     */
    public static final String[] SORT_BY = {"Nombre", "Fecha próxima temporada"};

    /**
     * Default new season date
     */
    public static final String DEFAULT_DATE = "1-1-2200";

    /**
     *  Imdb url to search series
     */
    public static final String IMDB_URL = "https://www.imdb.com";

    /**
     * Bundle key imdbID
     */
    public static final String SERIE_ID = "serieID";

    /**
     * TheMovieDB Api key
     */
    public static final String MOVIEDB_API = "7a17f554a9141c31009c76fc64ebdd1a";

    /**
     * TheMovieDB URL
     */
    public static final  String MOVIEDB_URL = "https://api.themoviedb.org/3";

    /**
     * SEARCH SERIE URL
     */
    public static final String SEARCH_SERIE_URL = "/tv/";

    /**
     * SEARCH SEASON URL
     */

    /**
     * serie poster URL
     */
    public static final String SERIE_POSTER = "https://image.tmdb.org/t/p/w500/";

    /**
     * serie external ids
     */
    public static final String EXTERNAL_IDS = "/external_ids?api_key=";

    /**
     * Default serie id (if user dont select a serie from list when adding the serie)
     */
    public static final int DEFAULT_ID = 0;

    /**
     * Default poster url
     */
    public static final String DEFAULT_POSTER_URL = "default_url";

    /**
     *  Calendar start time
     */
    public static final String CALENDAR_START_TIME = "7:00:00";

    /**
     *  Calendar start time
     */
    public static final String CALENDAR_END_TIME = "9:00:00";

    /**
     * Calendar request code
     */
    public static final int CALENDAR_REQUEST_CODE = 5;

    /**
     * Calendar permission request code
     */
    public static final int CALENDAR_PERMISSION_REQUEST_CODE = 10;

    /**
     * serie status ended
     */
    public static final String SERIE_STATUS_ENDED = "Ended";

    /**
     * Google sign in request code
     */
    public static final int GOOGLE_SIGN_IN = 25;

    /**
     * Serie status
     */
    public interface SERIE_STATUS {
        String ENDED = "Ended";
        String RETURNING = "Returning Series";
    }
}
