package com.example.oscarruiz.misseries.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.utils.Constants;

import org.w3c.dom.Text;


public class SerieInfoFragment extends Fragment {

    /**
     * Tab position
     */
    private int position;

    /**
     * Selected serie
     */
    private ResponseSerie serie;

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
        setInfo();

        return view;
    }

    /**
     * Method to get views
     */
    public void getViews(View view) {
        infoLayout = view.findViewById(R.id.info_layout);
        title = view.findViewById(R.id.title);
        rating = view.findViewById(R.id.rating);
        poster = view.findViewById(R.id.poster_imageview);
        year = view.findViewById(R.id.year_text_view);
        genre = view.findViewById(R.id.genre_text_view);
        seasons = view.findViewById(R.id.seasons_text_view);
        duration = view.findViewById(R.id.duration_text_view);
        serieEnded = view.findViewById(R.id.serie_ended);
        overview = view.findViewById(R.id.overview);
    }

    public void setPosition (int position, ResponseSerie serie) {
        this.position = position;
        this.serie = serie;
    }

    /**
     * Method to set info
     */
    public void setInfo() {

        if (position == 0){
            //set serie info
            infoLayout.setVisibility(View.VISIBLE);

            title.setText(serie.getName());
            rating.setText(String.valueOf(serie.getVoteAverage()));
            year.setText(serie.getAirDate());
            seasons.setText(String.valueOf(serie.getSeasons().size()));
            duration.setText(String.valueOf(serie.getEpisodeRunTime().get(0)));
            overview.setText(serie.getOverview());

            if (serie.getStatus().equals(Constants.SERIE_STATUS_ENDED)) {
                serieEnded.setVisibility(View.VISIBLE);
            } else {
                serieEnded.setVisibility(View.GONE);
            }

            //set poster
            Glide.with(SerieInfoFragment.this).load(Constants.SERIE_POSTER+serie.getPoster()).into(poster);

            //set serie genres
            if (serie.getGenres() != null){
                String genres = "";
                for (int i=0; i<serie.getGenres().size(); i++) {
                    genres =  genres + serie.getGenres().get(i).getGenre() + " ";
                }
                genre.setText(genres);
            }
        }
    }
}
