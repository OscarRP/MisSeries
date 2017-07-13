package com.example.oscarruiz.misseries.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.controllers.GetExternalId;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.utils.Constants;

public class WebViewActivity extends AppCompatActivity {

    /**
     * Serie id
     */
    private String id;

    /**
     * Imdb ID
     */
    private String imdbID;

    /**
     * Webview
     */
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getViews();
        loadURL();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        webView = (WebView)findViewById(R.id.webview);
    }

    /**
     * Method to load url in webView
     */
    private void loadURL() {
        //set webview as browser
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        //get serie id from bundle
        id = intent.getStringExtra(Constants.SERIE_ID);

        new GetExternalId(id, new AppInterfaces.ISearchExternalId() {
            @Override
            public void getExternalId(ResponseSerie responseSerie) {
                if (responseSerie != null && responseSerie.getImdbID() != null) {
                    //load url
                    webView.loadUrl(Constants.IMDB_URL+"/title/"+responseSerie.getImdbID());
                } else {
                    webView.loadUrl(Constants.IMDB_URL);
                }

            }
        }).execute();

    }

}
