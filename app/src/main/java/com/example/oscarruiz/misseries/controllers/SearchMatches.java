package com.example.oscarruiz.misseries.controllers;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.ArrayList;

/**
 * Created by Carlos Ruiz on 10/07/2017.
 */

public class SearchMatches  extends AsyncTask<Void, Void, ArrayList<ResponseSerie>> {

    /**
     * title to search
     */
    private String title;

    /**
     * Código de respuesta de la conexión
     */
    private int code_resp;

    /**
     * HttpUrlConnection
     */
    private HttpURLConnection httpURLConnection;

    /**
     * interface search matches
     */
    private AppInterfaces.ISearchMatches listener;

    /**
     * result series list
     */
    private ArrayList<ResponseSerie> resultList;

    public SearchMatches (String title, AppInterfaces.ISearchMatches listener) {
        this.title = title.replace(" ", "%20");
        this.listener = listener;

        resultList = new ArrayList<>();
    }


    @Override
    protected ArrayList<ResponseSerie> doInBackground(Void... voids) {

        try {
            //stablish connection
            URL url = new URL(Constants.MOVIEDB_URL + "/search/tv/?api_key=" + Constants.MOVIEDB_API + "&language=es&query=" + title);

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
                    for (int i=0; i<jsonArray.size(); i++) {
                        String selecedSerie = jsonArray.get(i).toString();

                        //convert jsonString to user
                        Gson gson = new Gson();
                        //add serie to list
                        resultList.add(gson.fromJson(selecedSerie, ResponseSerie.class));
                    }
                } else {
                    resultList = null;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    protected void onPostExecute(ArrayList<ResponseSerie> responseSeries) {
        super.onPostExecute(responseSeries);
        listener.getMatches(responseSeries);
    }
}
