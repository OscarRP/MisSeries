package com.example.oscarruiz.misseries.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.activities.DetailSerie;
import com.example.oscarruiz.misseries.controllers.SearchDetailSerie;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.utils.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Oscar Ruiz on 10/07/2017.
 */

public class ViewPagerMatchesAdapter extends PagerAdapter {

    /**
     * id series list
     */
    private ArrayList<ResponseSerie> idSeries;

    /**
     * Context
     */
    private Context context;

    public ViewPagerMatchesAdapter (Context context, ArrayList<ResponseSerie> idSeries) {
        this.context = context;
        this.idSeries = idSeries;
    }

    @Override
    public int getCount() {
        return idSeries.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup pager, int position) {

        //Actual view
        final View view;

        //inflate view
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.sugested_series_item, null);

        //set views
        final TextView titleTV = view.findViewById(R.id.title_text_view);
        final ImageView poster = view.findViewById(R.id.poster_image_view);
        final TextView yearTV = view.findViewById(R.id.year_text_view);
        final TextView genreTV = view.findViewById(R.id.genre_text_view);
        final TextView seasonsTV = view.findViewById(R.id.seasons_text_view);
        final TextView descriptionTV = view.findViewById(R.id.description_text_view);
        final RelativeLayout loadingLayout = view.findViewById(R.id.loading_layout);

        //set data to views
        loadingLayout.setVisibility(View.VISIBLE);
        new SearchDetailSerie(idSeries.get(position).getId(), new AppInterfaces.ISearchDetailSerie() {
            @Override
            public void getDetailSerie(ResponseSerie responseSerie) {

                if (responseSerie.getName() != null) {
                    titleTV.setText(responseSerie.getName());
                } else {
                    titleTV.setText(context.getResources().getString(R.string.no_info));
                }
                if (responseSerie.getAirDate() != null) {
                    yearTV.setText(String.valueOf(responseSerie.getAirDate().substring(0,4)));
                } else {
                    yearTV.setText(context.getResources().getString(R.string.no_info));
                }
                if (responseSerie.getGenres() != null) {
                    //set serie genres
                    String genres = "";
                    for (int i=0; i<responseSerie.getGenres().size(); i++) {

                        genres =  genres + responseSerie.getGenres().get(i).getGenre() + " ";
                    }
                    genreTV.setText(genres);
                } else {
                    genreTV.setText(context.getResources().getString(R.string.no_info));
                }

                if (responseSerie.getSeasons() != null) {
                    seasonsTV.setText(String.valueOf(responseSerie.getSeasons().size()));
                } else {
                    seasonsTV.setText(context.getResources().getString(R.string.no_info));
                }
                if (responseSerie.getOverview() != null) {
                    descriptionTV.setText(responseSerie.getOverview());
                } else {
                    descriptionTV.setText(context.getResources().getString(R.string.no_info));
                }
                if (responseSerie.getPoster() != null)  {
                    //set poster
                    Glide.with(context).load(Constants.SERIE_POSTER+responseSerie.getPoster()).into(poster);
                    loadingLayout.setVisibility(View.GONE);
                } else {
                    poster.setImageResource(R.mipmap.no_image);
                    loadingLayout.setVisibility(View.GONE);
                }
            }
        }).execute();

        //add view to viewpager
        pager.addView(view);

        return view;
    }

    /**
     * Method to delete viewpager view
     */
    @Override
    public void destroyItem(ViewGroup pager, int position, Object object) {
        pager.removeView((View) object);
    }
}
