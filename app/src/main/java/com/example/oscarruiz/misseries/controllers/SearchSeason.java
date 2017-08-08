package com.example.oscarruiz.misseries.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.Season;
import com.example.oscarruiz.misseries.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Oscar Ruiz on 04/08/2017.
 */

public class SearchSeason extends AsyncTask<Void, Void, Season> {

    /**
     * search season listener
     */
    private AppInterfaces.ISearchSeason listener;

    /**
     * Season
     */
    private Season season;

    /**
     * Serie id
     */
    private String serieID;

    /**
     * Season number
     */
    private String seasonNumber;

    /**
     * Conection response code
     */
    private int code_resp;

    /**
     * HttpUrlConnection
     */
    private HttpURLConnection httpURLConnection;

    public SearchSeason (String serieID, String seasonNumber, AppInterfaces.ISearchSeason listener) {
        this.serieID = serieID;
        this.seasonNumber = seasonNumber;
        this.listener = listener;
    }


    @Override
    protected Season doInBackground(Void... voids) {
        try {
            //stablish connection
            URL url = new URL(Constants.MOVIEDB_URL + "/tv/" + serieID + "/season/" + seasonNumber + "?api_key=" + Constants.MOVIEDB_API + "&language=es");
            Log.i("PRUEBA", "url: " + url.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            code_resp = httpURLConnection.getResponseCode();

            //Connection ok
            if (code_resp == httpURLConnection.HTTP_OK) {

                //read data
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //put info in buffer
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                //convert json to String
                String json_serie = buffer.toString();

                //convert jsonString to user
                Gson gson = new Gson();
                season = gson.fromJson(json_serie, Season.class);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return season;
    }

    @Override
    protected void onPostExecute(Season season) {
        super.onPostExecute(season);
        listener.getSeason(season);
    }
}
