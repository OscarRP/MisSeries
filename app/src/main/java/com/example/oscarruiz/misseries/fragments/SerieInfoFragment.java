package com.example.oscarruiz.misseries.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oscarruiz.misseries.R;

import org.w3c.dom.Text;


public class SerieInfoFragment extends Fragment {

    /**
     * season layout
     */
    private LinearLayout seasonLayout;

    /**
     * info layout
     */
    private LinearLayout infoLayout;

    /**
     * Serie title
     */
    private TextView title;

    /**
     * Serie rating
     */
    private TextView rating;

    /**
     * serie poster
     */
    private ImageView poster;

    /**
     * serie year
     */
    private TextView year;

    /**
     * serie genres
     */
    private TextView genre;

    /**
     * number of seasons
     */
    private TextView seasons;

    /**
     * chapter duration
     */
    private TextView duration;

    /**
     * is serie ended
     */
    private TextView serieEnded;

    /**
     * serie overview
     */
    private TextView overview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serie_info, container, false);

        getViews(view);

        return view;
    }

    /**
     * Method to get views
     */
    private void getViews(View view) {
        infoLayout = view.findViewById(R.id.info_layout);
        title = (TextView)view.findViewById(R.id.title);
        rating = view.findViewById(R.id.rating);
        poster = view.findViewById(R.id.poster_imageview);
        year = view.findViewById(R.id.year_text_view);
        genre = view.findViewById(R.id.genre_text_view);
        seasons = view.findViewById(R.id.seasons_text_view);
        duration = view.findViewById(R.id.duration_text_view);
        serieEnded = view.findViewById(R.id.endedresponse);
        overview = view.findViewById(R.id.overview);

        seasonLayout = view.findViewById(R.id.season_layout);


    }

    /**
     * Method to set info
     */
    public void setInfo() {

    }
}
