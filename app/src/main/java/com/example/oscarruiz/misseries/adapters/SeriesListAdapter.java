package com.example.oscarruiz.misseries.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.activities.HomeActivity;
import com.example.oscarruiz.misseries.controllers.SearchDetailSerie;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.models.Serie;
import com.example.oscarruiz.misseries.utils.Constants;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Ruiz on 29/06/2017.
 */

public class SeriesListAdapter extends BaseAdapter {

    /**
     * Interface deleting serie
     */
    private AppInterfaces.IPressDeleteSerie listener;

    /**
     * Viewholder var
     */
    private ViewHolder viewHolder;

    /**
     * Layout inflater
     */
    private LayoutInflater inflater;

    /**
     * boolean that indicates if user is going to delete a serie
     */
    private boolean isDeleting;

    /**
     * Series list
     */
    private ArrayList<Serie> series;

    /**
     * Context
     */
    private Context context;

    public SeriesListAdapter(ArrayList<Serie> series, Context context, boolean isDeleting, AppInterfaces.IPressDeleteSerie listener) {
        this.series = series;
        this.context = context;
        this.isDeleting = isDeleting;
        this.listener = listener;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return series.size();
    }

    @Override
    public Object getItem(int i) {
        return series.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            //inflate view
            view = inflater.inflate(R.layout.series_list_item, viewGroup, false);

            //initialize viewholder
            viewHolder = new ViewHolder();

            //getviews
            viewHolder.name = (TextView)view.findViewById(R.id.name);
            viewHolder.newSeason = (TextView)view.findViewById(R.id.new_season);
            viewHolder.deleteButton = (ImageButton)view.findViewById(R.id.delete_button);
            viewHolder.posterImageView = view.findViewById(R.id.poster_image_view);

            //set tag
            view.setTag(viewHolder);
        } else {
            //get holder
            viewHolder = (ViewHolder)view.getTag();
        }

        //show delete button if isDeleting
        if (isDeleting) {
            viewHolder.deleteButton.setVisibility(View.VISIBLE);

            //delete button listener
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Show Alert Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getResources().getString(R.string.title));
                    builder.setMessage(context.getResources().getString(R.string.description));
                    builder.setPositiveButton(context.getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //send positin to delete serie
                            listener.isDeleting(position);
                        }
                    });
                    builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            });
        }

        //set info
        viewHolder.name.setText(WordUtils.capitalize(series.get(position).getTitle()));
        //if serie has not new season date selected by user, show "no info"
        if (!series.get(position).getNewSeason().equals(Constants.DEFAULT_DATE)) {
            viewHolder.newSeason.setText(series.get(position).getNewSeason());
        } else {
            viewHolder.newSeason.setText(view.getResources().getString(R.string.no_info));
        }

        //set poster
        Glide.with(context).load(Constants.SERIE_POSTER + series.get(position).getPosterURL()).into(viewHolder.posterImageView);

        return view;
    }

    private class ViewHolder {
        private TextView name;
        private TextView newSeason;
        private ImageButton deleteButton;
        private ImageView posterImageView;
    }
}
