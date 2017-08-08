package com.example.oscarruiz.misseries.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.controllers.SearchSeason;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.models.Season;
import com.example.oscarruiz.misseries.utils.Constants;

import org.w3c.dom.Text;

public class SerieSeasonFragment extends Fragment {

    /**
     * Poster ImageView
     */
    private ImageView posterIV;

    /**
     * Season overview
     */
    private TextView overview;

    /**
     * Season
     */
    private Season season;

    /**
     * Tab position
     */
    private int position;

    /**
     * Selected serie
     */
    private ResponseSerie serie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serie_season, container, false);

        getViews(view);
        setInfo();

        return view;
    }

    private void getViews(View v) {
        overview = v.findViewById(R.id.overview);
        posterIV = v.findViewById(R.id.poster_imageview);

    }

    /**
     * Method to set tab position
     */
    public void setPosition (int position, ResponseSerie serie) {
        this.position = position;
        this.serie = serie;
    }

    /**
     * Method to set fragment info
     */
    private void setInfo() {

        new SearchSeason(String.valueOf(serie.getId()), String.valueOf(position), new AppInterfaces.ISearchSeason() {
            @Override
            public void getSeason(Season season) {
                overview.setText(season.getOverview());
                //set poster
                Glide.with(SerieSeasonFragment.this).load(Constants.SERIE_POSTER+season.getSeasonPoster()).into(posterIV);
            }
        }).execute();
    }
}
