package com.example.oscarruiz.misseries.controllers;

import android.os.AsyncTask;

import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
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
 * Created by Carlos Ruiz on 07/07/2017.
 */

public class SearchSerie extends AsyncTask<Void, Void, ResponseSerie> {

    /**
     * Isearch serie listener
     */
    private AppInterfaces.ISearchSerie listener;

    /**
     * title to search
     */
    private String title;

    /**
     * OmdbSerie
     */
    private ResponseSerie responseSerie;

    /**
     * Código de respuesta de la conexión
     */
    private int code_resp;

    /**
     * HttpUrlConnection
     */
    private HttpURLConnection httpURLConnection;


    public SearchSerie(String title, AppInterfaces.ISearchSerie listener) {
        this.title = title.replace(" ", "%20");
        this.listener = listener;
    }

    @Override
    protected ResponseSerie doInBackground(Void... voids) {

        try {
            //stablish connection
            URL url = new URL(Constants.MOVIEDB_URL + "/search/tv/?api_key=" + Constants.MOVIEDB_API + "&language=es&query=" + title + "&page=1");

            httpURLConnection = (HttpURLConnection) url.openConnection();

            //Redirection if url is moved
            String redirect = httpURLConnection.getHeaderField("Location");
            if (redirect != null) {
                httpURLConnection = (HttpURLConnection) new URL(redirect).openConnection();
            }

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

                //take field results and first item
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(json_serie).getAsJsonObject();
                JsonArray jsonArray = object.get("results").getAsJsonArray();

                if (jsonArray.size() > 0) {
                    String selecedSerie = jsonArray.get(0).toString();

                    //convert jsonString to user
                    Gson gson = new Gson();
                    responseSerie = gson.fromJson(selecedSerie, ResponseSerie.class);
                } else {
                    responseSerie = null;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseSerie;
    }

    @Override
    protected void onPostExecute(ResponseSerie responseSerie) {
        super.onPostExecute(responseSerie);
        listener.getSerie(responseSerie);
    }
}

