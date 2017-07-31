package com.example.oscarruiz.misseries.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.utils.Constants;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Oscar Ruiz on 07/07/2017.
 */

public class GetExternalId  extends AsyncTask<Void, Void, ResponseSerie> {

    /**
     * Search external ids listener
     */
    private AppInterfaces.ISearchExternalId listener;

    /**
     * Serie id
     */
    private String id;

    /**
     * serie
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

    public GetExternalId(String id, AppInterfaces.ISearchExternalId listener) {
        this.id = id;
        this.listener = listener;
    }
    @Override
    protected ResponseSerie doInBackground(Void... voids) {
        try {
            //stablish connection
            URL url = new URL(Constants.MOVIEDB_URL+Constants.SEARCH_SERIE_URL+id+Constants.EXTERNAL_IDS+Constants.MOVIEDB_API);

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

                //convert jsonString to user
                Gson gson = new Gson();
                responseSerie = gson.fromJson(json_serie, ResponseSerie.class);
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
        listener.getExternalId(responseSerie);
    }
}
